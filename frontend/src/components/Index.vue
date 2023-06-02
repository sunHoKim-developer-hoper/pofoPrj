<script setup>
import { reactive, onMounted, watch, onBeforeMount,onBeforeUnmount } from 'vue';
import Header from './Header.vue';
import 'vue3-carousel/dist/carousel.css'
import { Carousel, Slide, Pagination, Navigation } from 'vue3-carousel'
import { useRouter, useRoute, onBeforeRouteUpdate } from 'vue-router';
import { useUserDetailsStore } from '../stores/useUserDetailsStore';

// --- Variables ---------------------------------------
let portfolio = reactive({
  list: [], // 포트폴리오 리스트를 담을 변수
  sort: 'latest', // 정렬방식을 나타내는 변수, 최신순이 디폴트
  collaboration: null, // 협업여부를 나타내는 변수, 전체가 디폴트
  language: null, // 프로그래밍언어를 나타내는 변수, 전체가 디폴트
  query: null, // 검색어를 담을 변수, 

  weeklyPopularList: [], // 이번주 인기 포트폴리오 리스트를 담을 변수
  offset: 0, // 다음 리스트를 가져오기 위한 오프셋 값
});

let userDetails = useUserDetailsStore();
let router = useRouter();
let route = useRoute();

// --- Life Cycles -------------------------------------
onBeforeMount(()=>{
  if(route.path == "/")
    router.push("/index")
})
onMounted(async ()=>{
  if(route.query.q)
    portfolio.query = route.query.q;
  await fetchPortfolios();
});
watch(() => [portfolio.sort, portfolio.collaboration, portfolio.language, portfolio.query], fetchPortfolios); // 변수가 변경될 때마다 함수 실행

// 스크롤 이벤트 리스너 등록
onMounted(() => {
  window.addEventListener('scroll', handleScroll);
});

onBeforeUnmount(() => {
  window.removeEventListener('scroll', handleScroll);
});

onBeforeRouteUpdate((to, from, next) => {
  if (to.query.q != from.query.q) {
        portfolio.query = to.query.q;
        fetchPortfolios();
        next();
  }
  else if(!to.query.q){
    fetchPortfolios();
  }
});


// --- Event Handlers ----------------------------------
function queryUpdateHandler(query) {
  portfolio.query = query;
}

async function fetchPortfolios() {
  const url = new URL('http://localhost:8080/index');
  url.searchParams.set('sort', portfolio.sort); // URL의 query string을 처리하는 함수
  if (portfolio.collaboration !== null) { // 협업여부를 선택한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('collaboration', portfolio.collaboration);
  }
  if (portfolio.language !== null) { // 프로그래밍언어를 선택한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('language', portfolio.language);
  }
  if (portfolio.query !== null) { // 검색한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('query', portfolio.query);
  }

  let response = await fetch(url);
  let json = await response.json();
  portfolio.list = json.list;
  portfolio.weeklyPopularList = json.weeklyPopularList;
}

async function refetchPortfolios(offset) {
  const url = new URL('http://localhost:8080/index');
  url.searchParams.set('offset', offset);
  url.searchParams.set('sort', portfolio.sort); // URL의 query string을 처리하는 함수
  if (portfolio.collaboration !== null) { // 협업여부를 선택한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('collaboration', portfolio.collaboration);
  }
  if (portfolio.language !== null) { // 프로그래밍언어를 선택한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('language', portfolio.language);
  }
  if (portfolio.query !== null) { // 검색한 경우 쿼리 파라미터를 추가함
    url.searchParams.set('query', portfolio.query);
  }

  let response = await fetch(url);
  let json = await response.json();
  for(let item of json.list)
    portfolio.list.push(item);
}


function profileIdClickHandler(e, memberId){
  if(memberId === userDetails.id)
    router.push("/member/profile/"+memberId);
  else
    router.push("profile/"+memberId);
}

async function portfolioClickHandler(id) {
  const url = new URL(`http://localhost:8080/pofo/update/${id}`);
  await fetch(url);
}


function handleScroll(){
  const documentHeight = document.documentElement.offsetHeight;
  const scrollPosition = window.innerHeight + window.pageYOffset;

  if (scrollPosition >= documentHeight) {
    portfolio.offset += 15;
    refetchPortfolios(portfolio.offset);
  }
};

</script>

<template>
  <Header @query-updated="queryUpdateHandler" />
  <main>
    <!-- 이번주 인기 TOP 10 포트폴리오 리스트 -->
    <section v-if="portfolio.query == null" class="slider-container">
      <h1 class="d-none">이번주 인기 TOP 10</h1>
      <div class="slider">
        <Carousel :itemsToShow="3.125" :wrapAround="true" :transition="500">
          <Slide v-for="(portfolio, index) in portfolio.weeklyPopularList" :key="index">
            <router-link 
                        :to="'/pofo/' + portfolio.id" 
                        class="carousel__item"
                        @click.prevent="portfolioClickHandler(portfolio.id)"
                        >
              <img :src="'http://localhost:8080/portfolio/thumbnails/' + portfolio.thumbnail" alt="포트폴리오 섬네일 이미지">
              <div class="overlay">
                <h2>{{ portfolio.title }}</h2>
                <p>{{ portfolio.nickname }}</p>
              </div>
            </router-link>
          </Slide>

          <template #addons>
            <navigation />
            <pagination />
          </template>
        </Carousel>
      </div>
    </section>

    <!-- 포트폴리오 리스트 -->
    <div class="portfolio-container">

      <!-- 필터링 -->
      <section class="category-section">
        <h1 v-if="portfolio.query == null">POFO의 인기 개발언어를 선택해 보세요.</h1>
        <h1 v-if="portfolio.query != null">'{{ portfolio.query }}' 에 대한 검색 결과입니다.</h1>
        <!-- 프로그래밍 언어별 -->
        <ul v-if="portfolio.query == null" class="category-list">
          <li class="category-item entire" :class="{ active: portfolio.language === null }" @click="portfolio.language = null">
            <button>
              <span>전체</span>
            </button>
          </li>
          <li class="category-item java" :class="{ active: portfolio.language === 1 }" @click="portfolio.language = 1">
            <button>
              <span>Java</span>
            </button>
          </li>
          <li class="category-item javascript" :class="{ active: portfolio.language === 2 }" @click="portfolio.language = 2">
            <button>
              <span>JavaScript</span>
            </button>
          </li>
          <li class="category-item python" :class="{ active: portfolio.language === 3 }" @click="portfolio.language = 3">
            <button>
              <span>Python</span>
            </button>
          </li>
          <li class="category-item c" :class="{ active: portfolio.language === 4 }" @click="portfolio.language = 4">
            <button>
              <span>C</span>
            </button>
          </li>
          <li class="category-item c-sharp" :class="{ active: portfolio.language === 5 }" @click="portfolio.language = 5">
            <button>
              <span>C#</span>
            </button>
          </li>
          <li class="category-item visual-basic" :class="{ active: portfolio.language === 6 }" @click="portfolio.language = 6">
            <button>
              <span>Visual Basic</span>
            </button>
          </li>
        </ul>

        <div class="options-wrap">
          <!-- 정렬방식 -->
          <div class="sorting-options">
            <span>정렬방식:</span>
            <button class="sort-button" :class="{ active: portfolio.sort === 'latest' }"
              @click="portfolio.sort = 'latest'">최신순</button>
            <button class="sort-button" :class="{ active: portfolio.sort === 'likes' }"
              @click="portfolio.sort = 'likes'">좋아요순</button>
          </div>
          <!-- 협업여부 -->
          <div class="collaboration-options">
            <span>협업여부:</span>
            <button class="collaboration-button" :class="{ active: portfolio.collaboration === null }"
              @click="portfolio.collaboration = null">전체</button>
            <button class="collaboration-button" :class="{ active: portfolio.collaboration === 0 }"
              @click="portfolio.collaboration = 0">팀</button>
            <button class="collaboration-button" :class="{ active: portfolio.collaboration === 1 }"
              @click="portfolio.collaboration = 1">개인</button>
          </div>
        </div>
      </section>

      <!-- 리스트 섹션 -->
      <section class="list-section" ref="listSection">
        <h1 class="d-none">포트폴리오 리스트 섹션</h1>
        <ul>
          <li v-for="(portfolio, index) in portfolio.list" :key="index">
            <router-link 
                        :to="'/pofo/' + portfolio.id" 
                        class="thumbnail" 
                        :data-title="portfolio.title"
                        @click.prevent="portfolioClickHandler(portfolio.id)"
                        >
              <img :src="'http://localhost:8080/portfolio/thumbnails/' + portfolio.thumbnail" alt="포트폴리오 섬네일 이미지">
            </router-link>
            <div class="information">
              <div class="portfolio-info-profile">
                <span @click.prevent="profileIdClickHandler($event,portfolio.memberId)" class="pointer">
                  <img src="/src/assets/images/proflie.svg" alt="마이프로필"
                    v-if="portfolio.memberImage == null" />
                  <img :src="'http://localhost:8080/profileImage/' + portfolio.memberImage" alt="프로필 이미지" 
                    v-else>
                </span>
                <span @click.prevent="profileIdClickHandler($event,portfolio.memberId)" class="pointer">{{ portfolio.nickname }}</span>
              </div>
              <div class="portfolio-info-counts">
                <img src="/src/assets/images/eye.png" alt="조회수 이미지">
                <span class="hit">{{ portfolio.hit }}</span>
                <img src="/src/assets/images/heart.png" alt="하트 이미지">
                <span class="like">{{ portfolio.likeCount }}</span>
              </div>
            </div>
          </li>
        </ul>
      </section>
    </div>
  </main>
</template>

<style scoped>
@import url("/src/assets/css/compoment/index.css");
</style>