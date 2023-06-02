<script setup>
import Header from "@/components/Header.vue";
import { reactive, onMounted } from "vue";
import { useRouter } from "vue-router";

// --- Variables ---------------------------------------
let community = reactive({
  list: [], // 커뮤니티 리스트를 담을 변수
});

let router = useRouter();

// --- Life Cycles -------------------------------------
onMounted(fetchCommunities);

// --- Event Handlers ----------------------------------
async function fetchCommunities() {
  const url = new URL("http://localhost:8080/community/list");

  let response = await fetch(url);
  let json = await response.json();
  community.list = json.list;
}

function communityClickHandler(id) {
  router.push("/community/" + id);
}
</script>

<template>
  <Header />
  <div class="min-h-full px-4 pt-24 md:px-16 xl:min-w-full xl:pb-8 xl:pt-24">
    <figure class="flex items-center justify-center xl:mb-20">
      <img
        src="/src/assets/images/butterfly.png"
        alt="Logo"
        class="w-8 sm:mr-2 sm:mt-2 sm:w-12 xl:w-20"
      >
      <figcaption
        class="py-16 text-center text-2xl font-extrabold md:text-5xl xl:text-7xl"
      >
        POFO COMMUNITY
      </figcaption>
    </figure>
    <div class="flex h-20 flex-col justify-evenly xl:h-36">
      <h1 class="text-xl font-extrabold xl:text-5xl">
        POFO 커뮤니티
      </h1>
      <p class="text-xs font-bold xl:text-xl">
        POFO 커뮤니티는 프로젝트 팀원을 모집하는 공간입니다.
      </p>
      <p class="text-xs font-bold xl:text-xl">
        팀으로 프로젝트를 진행하고 싶으시다면 언제든 환영입니다!
      </p>
    </div>
    <div class="mt-8 flex h-14 flex-col justify-evenly xl:h-20">
      <div class="flex items-center">
        <img
          src="/src/assets/images/group.png"
          alt="User group icon"
          class="mr-2 w-8"
        >
        <h2 class="text-xl font-extrabold xl:text-3xl">
          팀원 모집
        </h2>
      </div>
      <p class="text-xs font-medium xl:text-base">
        현재 모집중인 팀입니다.
      </p>
    </div>

    <!-- Recruit list -->
    <div
      class="grid gap-x-6 gap-y-6 md:grid-cols-3 lg:grid-cols-4 xl:my-4 xl:grid-cols-5 xl:grid-rows-2"
    >
      <figure
        class="w-80 cursor-pointer rounded-xl border-2 shadow-md sm:w-64 xl:w-full"
        v-for="(community, index) in community.list"
        :key="index"
        @click.prevent="communityClickHandler(community.id)"
      >
        <img
          :src="`http://localhost:8080/communityImage/` + community.thumbnail"
          alt="Recruit thumbnail"
          class="rounded-t-lg h-52 w-full object-cover"
        >
        <figcaption class="px-4 py-4">
          <h3
            class="text-md block truncate font-semibold"
            v-text="community.title"
          />
          <h3 class="float-right closed" v-if="community.closeFlag==1">모집 마감</h3>
          <h3 class="float-right non-closed" v-else>모집 중</h3>
          <span
            class="sm:text:lg relative right-4 block w-24 rounded-tr-lg bg-white text-center font-bold sm:bottom-12 xl:bottom-14 xl:text-xl"
            v-if="community.onlineType === true"
          >ON-LINE</span>
          <span
            class="sm:text:lg relative right-4 block w-24 rounded-tr-lg bg-white text-center font-bold sm:bottom-12 xl:bottom-14 xl:text-xl"
            v-if="community.onlineType === false"
          >OFF-LINE</span>
          <div class="flex flex-col justify-between sm:h-16 xl:h-20">
            <!-- <span class="block w-fit rounded-md bg-blue-100 px-1 text-sm" v-text="`${community.memberId}`" /> -->
            <!-- <span class="block truncate text-sm" v-text="`${community.teamSize}`명" /> -->
            <div>
              <span class="w-fit px-1 text-sm font-bold">장소 </span>
              <span
                class="w-fit rounded-md bg-red-100 px-1 text-sm"
                v-text="`${community.locationInfo}`"
              />
            </div>
            <div>
              <span class="w-fit px-1 text-sm font-bold">기간 </span>
              <span
                class="w-fit rounded-md bg-red-100 px-1 text-sm"
                v-text="`${community.period}`"
              />
            </div>
            <div>
              <span class="w-fit px-1 text-sm font-bold">인원 </span>
              <span
                class="w-fit rounded-md bg-blue-100 px-1 text-sm"
                v-text="`${community.teamSize}명`"
              />
            </div>
          </div>
        </figcaption>
      </figure>
    </div>
  </div>
</template>

<style scoped>
@import url("/src/assets/css/tailwind.css");
.float-right{
  float: right !important;
}
h3 {
  display: inline-block !important;
}

.closed{
  background-color: gray;
  font-size: 13px;
  font-weight: 400;
  color: white;
  padding: 4px;
}

.non-closed{
  background-color: #fdecc8;
  font-size: 13px;
  font-weight: 400;
  color: #49290e;
  padding: 4px;
}
</style>