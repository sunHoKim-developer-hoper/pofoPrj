<template>
  <header class="com-header" v-if="!userDetails.isAuthenticated">
    <ul class="menu-bar">
      <li>
        <router-link to="/index" @click="removeQuery"><img class="header-logo" src="/src/assets/images/Pofo.svg" alt="logo" /></router-link>
      </li>
      <li class="pro"><a href="">프로젝트</a></li>
      <li class="community">
        <router-link to="/community/list">커뮤니티</router-link>
      </li>
    </ul>
    <div class="search-box">
      <img class="search-img" src="/src/assets/images/free-icon-search-482631-3.svg" alt="돋보기" /><input
        class="header-search" type="text" placeholder="검색어를 입력해보세요" v-model = "query"
        @keydown.enter="search()" />
    </div>
    <ul class="m-menu user-bar">
      <li class="login-li">
        <router-link :to="loginUrl">로그인</router-link>
      </li>
      <li class="sign-up-li">
        <router-link to="/signup">
          <button class="btn btn-3 sign-up">회원가입</button>
        </router-link>
      </li>
    </ul>
  </header>
  <!-- 맴버 -->
  <header class="com-header" v-if="userDetails.isAuthenticated">
    <ul class="menu-bar">
      <li>
        <router-link to="/index"><img class="header-logo" src="/src/assets/images/Pofo.svg" alt="logo" /></router-link>
      </li>
      <li class="pro"><a href="">프로젝트</a></li>
      <li class="community">
        <router-link to="/community/list">커뮤니티</router-link>
      </li>
    </ul>
    <div class="search-box">
      <img class="search-img" src="/src/assets/images/free-icon-search-482631-3.svg" alt="돋보기" /><input
        class="header-search" type="text" placeholder="검색어를 입력해보세요" v-model = "query"
        @keydown.enter="search()" />
    </div>
    <ul class="m-menu member-bar">
      <li v-if="route.path.includes('/community')">
        <router-link to="/member/community/register">
          <button class="btn btn-3 prj-register">
            커뮤니티 등록
          </button>
        </router-link>
      </li>
      <li v-else>
        <router-link to="/member/reg">
          <button class="btn btn-3 prj-register">
            프로젝트 등록
          </button>
        </router-link>
      </li>
      <button @click="showModalMessage">
        <li><img class="dm" src="/src/assets/images/email.png" alt="DM" /></li>
      </button>
      <button @click="showModalNotify">
        <li>
          <img class="bell" src="/src/assets/images/bell.png" alt="알림" />
        </li>
      </button>
      <li>
        <button @click="showModalProfile">
          <img class="header-profile" src="/src/assets/images/proflie.svg" alt="마이프로필"
            v-if="userDetails.profileSrc == null" />
          <img :src="'http://localhost:8080/profileImage/' + userDetails.profileSrc" class="header-profile" v-else />
        </button>
      </li>
      <li>
        <img class="hamburger-bar" src="/src/assets/images/menu.png" alt="" />
      </li>
    </ul>
  </header>
  <div class="head-popup" :class="modalChange" v-if="isModalOpenMessage" v-on-click-outside="closeModal">
    <div class="noti-list" @click.stop>
      <div class="noti-header">
        <div class="noti-title-content">
          메시지<a href="#" class="font-size-14">모든메시지 보기 ></a>
        </div>
        <div class="alarm-item">
          <div class="alarm-content">
            <div class="modal-wrap">
              <div class="modal-thumbnail">
                <div class="modal-thumbnail-circle">
                  <div class="profile-image-wrap">
                    <img src="/src/assets/images/proflie.svg" class="profile-img-msg" />
                  </div>
                </div>
              </div>
              <div class="modal-wall"></div>
              <div class="modal-content">
                <div class="modal-title">
                  <div class="modal-name">
                    <p class="modal-nickname">JongWoo</p>
                  </div>
                  <div class="modal-date">2023년 04월 21일</div>
                </div>
                <div class="modal-title-wall"></div>
                <div class="modal-last-msg">
                  <p class="modal-msg-text" title="아아아아아">아아아아아</p>
                </div>
              </div>
            </div>
            <div class="bottom-line"></div>
          </div>
        </div>
      </div>
    </div>
  </div>
  <div class="head-popup" :class="modalChange" v-if="isModalOpenNotify" v-on-click-outside="closeModal">
    <div class="noti-list">
      <div class="noti-header">
        <div class="noti-title-content">
          알림
        </div>

        <!-- v-for 시작 지점 -->
        <div class="alarm-item" v-for="(notification, index) in notificationList">
          <div class="alarm-content">
            <div class="modal-wrap">
              <div class="modal-thumbnail">
                <div class="modal-thumbnail-circle">
                  <div class="profile-image-wrap">
                    <router-link :to="'/profile/' + notification.fromMemberId" v-if="notification.image == null"
                      @click="closeModal"><img src="/src/assets/images/proflie.svg"
                        class="profile-img-noti hover radiu-50" /></router-link>
                    <router-link :to="'/profile/' + notification.fromMemberId" v-else @click="closeModal"><img
                        :src="'http://localhost:8080/profileImage/' + notification.image"
                        class="profile-img-noti hover radiu-50" /></router-link>
                  </div>
                </div>
              </div>
              <div class="modal-wall"></div>
              <div class="modal-content">
                <div class="modal-title">
                  <router-link
                    :to="notification.url + (notification.typeId > 5 ? notification.communityId : (notification.typeId > 2) ? notification.fromMemberId : notification.portfolioId)">
                    <p class="modal-msg-text-notify hover width-300px" title="Thomas님이 좋아요를 누르셨습니다."
                      @click="changeClickFlag($event, index)" :class="{ 'readed': notification.clickFlag == 1 }">
                      {{ notification.nickname + notification.text }}
                    </p>
                  </router-link>
                  <div class="modal-date">{{ notification.regDate.substring(0, 10) }}</div>
                  <span class="hover" style="font-weight: bold;" @click="removeNotification($event, index)">X</span>
                </div>
                <button v-if="notification.typeId == 4 && notification.acceptFlag == 0" class="element" @click.prevent="accetpBtn(index)">수락하기</button>
                <button v-if="notification.typeId == 4 && notification.acceptFlag == 0" class="element" @click.prevent="rejectBtn(index)">거절하기</button>
                <button v-if="notification.typeId == 4 && notification.acceptFlag == 1" class="element2">수락 완료</button>
                <button v-if="notification.typeId == 4 && notification.acceptFlag == 2" class="element2">반려 완료</button>
                <!--수락 거절 버튼 클릭 하고 싶으면-->
                <div class="modal-title-wall"></div>
              </div>
            </div>
            <div class="bottom-line"></div>
          </div>
        </div>

      </div>
    </div>
  </div>
  <div class="head-popup" :class="modalChange" v-if="isModalOpenProfile" v-on-click-outside="closeModal">
    <div class="noti-list" @click.stop>
      <div class="noti-header">
        <div class="user-main-wrap">
          <div class="profile-image-wrap" v-if="userDetails.profileSrc == null">
            <img src="/src/assets/images/proflie.svg" class="profile-img-mypage" />
          </div>
          <div class="profile-image-wrap" v-else>
            <img :src="'http://localhost:8080/profileImage/' + userDetails.profileSrc" class="profile-img-mypage" />
          </div>
          <div class="user-detail-wrap">
            <div class="user-detail-nickname">{{ userDetails.nickname }}</div>
            <div class="user-detail-email">{{ userDetails.email }}</div>
          </div>
        </div>
        <div class="profile-menu-wrap">
          <router-link to="/member/reg" class="upload menu-item margin-top-5">
            새로운 포트폴리오 업로드
          </router-link>
          <router-link :to="'/member/profile/' + userDetails.id" class="mypofo menu-item">
            나의 포트폴리오
          </router-link>
          <div class="line-bar"></div>
          <a href="#" class="menu-item top-border-line" @click.prevent="logoutHandler">로그아웃</a>
        </div>
      </div>
    </div>
  </div>
</template>



<style>
@import url("/src/assets/css/common/reset.css");
@import url("/src/assets/css/common/style.css");
@import url("/src/assets/css/common/util.css");
@import url("/src/assets/css/common/buttons.css");
@import url("/src/assets/css/compoment/header-modal.css");
@import url("/src/assets/css/compoment/header.css");

.profile-img {
  width: 28px;
  height: 28px;
}

.hover:hover {
  cursor: pointer;
}

.width-300px {
  width: 300px;
}

.radiu-50 {
  border-radius: 50px;
  object-fit: cover
}

.readed {
  color: gray;
}

.element {
  margin-left: 1px;
  color: white;
  background-color: rgb(42, 197, 198);
  border-radius: 4px;
  transition: box-shadow 0.3s ease;
}

.element:hover {
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.3);
  /* 호버 시 그림자 효과 추가 */
}

.element2{
  margin-left: 1px;
  color: white;
  background-color: black;
  border-radius: 4px;
  transition: box-shadow 0.3s ease;
}
</style>

<script setup>
import { vOnClickOutside } from '@vueuse/components'
import { ref, reactive, onMounted, onUpdated, defineEmits } from 'vue';
import { useUserDetailsStore } from '../stores/useUserDetailsStore';
import { useRouter, useRoute } from 'vue-router';

let isModalOpenMessage = ref(false)
let isModalOpenNotify = ref(false)
let isModalOpenProfile = ref(false)
let modalChange = ref("");
let userDetails = useUserDetailsStore();
let router = useRouter();
let route = useRoute();

let query = ref();

let loginUrl = ref("");
onMounted(async () => {
  if (route.path != '/login') {
    let returnURL = route.path;
    loginUrl.value = '/login?returnURL=' + returnURL;
  }
  await load();
});

let notiText = reactive(['님이 좋아요를 누르셨습니다.',
  '님이 댓글을 다셨습니다.',
  '님이 북마크 등록을 하였습니다.',
  '님이 팔로우하였습니다.',
  '님이 참여하기를 선택하였습니다.',
  '님이 참여 수락을 하였습니다.']);


let notificationList = reactive([]);

async function load() {
  if (userDetails.isAuthenticated) {
    let response = await fetch("http://localhost:8080/notifications/list/" + userDetails.id);
    let notiList = await response.json();
    notificationList = notiList;
    for (let item of notificationList)
      item.text = notiText[item.typeId];
  }
}

function showModalMessage() {
  isModalOpenNotify.value = false;
  isModalOpenMessage.value = true;
  isModalOpenProfile.value = false;
  modalChange.value = "noti-content-message";
}
function showModalNotify() {
  isModalOpenMessage.value = false;
  isModalOpenNotify.value = true;
  isModalOpenProfile.value = false;
  modalChange.value = "noti-content-notify";
}
function showModalProfile() {
  isModalOpenMessage.value = false;
  isModalOpenNotify.value = false;
  isModalOpenProfile.value = true;
  modalChange.value = "noti-content-profile";
}

function closeModal() {
  isModalOpenMessage.value = false;
  isModalOpenNotify.value = false;
  isModalOpenProfile.value = false;
}

function logoutHandler() {
  userDetails.logout();
  isModalOpenMessage.value = false;
  isModalOpenNotify.value = false;
  isModalOpenProfile.value = false;
  router.push("/index");
}


async function changeClickFlag(e, index) {
  await fetch(`http://localhost:8080/notifications/changeflag/${notificationList[index].id}`, {
    method: "PUT"
  });

}

async function removeNotification(e, index) {
  let res = await fetch(`http://localhost:8080/notifications/remove/${notificationList[index].id}`, {
    method: "DELETE"
  });
  let result = await res.text();
  if (result == 'ok') {
    closeModal();
    await load();
    isModalOpenNotify.value = true;
  }
}

async function accetpBtn(index) {
  let res = await fetch(`http://localhost:8080/community/accept`, {
    method: "POST",
    headers: {
      "Content-type": "application/x-www-form-urlencoded"
    },
    body: `memberId=${notificationList[index].fromMemberId}&communityId=${notificationList[index].communityId}&id=${notificationList[index].id}`
  })
  let result = await res.text();
  if (result == 'ok') {
    closeModal();
    await load();
    isModalOpenNotify.value = true;
  }
}

async function rejectBtn(index) {
  let res = await fetch(`http://localhost:8080/community/reject`, {
    method: "POST",
    headers: {
      "Content-type": "application/x-www-form-urlencoded"
    },
    body: `memberId=${notificationList[index].fromMemberId}&communityId=${notificationList[index].communityId}&id=${notificationList[index].id}`
  })
  let result = await res.text();
  if (result == 'ok') {
    closeModal();
    await load();
    isModalOpenNotify.value = true;
  }
}

  function search() {
      router.push('/index?q='+query.value);
  }

  function removeQuery(){
    query.value = '';
  }
</script>
