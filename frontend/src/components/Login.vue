<script setup>
import { reactive, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import { useUserDetailsStore } from "../stores/useUserDetailsStore";
import Modal from './Modal.vue';


//--------------데이터
let router = useRouter();
let route = useRoute();
let userDetails = useUserDetailsStore();
let showModal = ref(false);

let user = reactive({
  email: "",
  password: "",
});

//-------------소셜로그인
async function googleLoginHandler(response) {
  await fetch(
    `https://www.googleapis.com/oauth2/v3/userinfo?access_token=${response.access_token}`
  )
    .then((res) => res.json())
    .then((credential) => {
      userDetails.email = credential.email;
    });

  //구글 아이디로 회원가입을 한적이 있는지 확인해야함
  let isDupulicated = await fetch(
    `http://localhost:8080/email/checkemail?email=${userDetails.email}`
  );
  let result = await isDupulicated.text();
  //우리 DB에 없을 시 회원가입 화면으로 넘어가진다.
  if (result === "ok") {
    router.push("/signup?type=oauth");
    return;
  }

  let res = await fetch(`http://localhost:8080/user/login?email=${userDetails.email}`, {
    method: "GET",
    headers: {
      Accept: "application/json",
    },
  });
  let json = await res.json();
  saveStores(json);

  routerLogic();
}

//-------------이벤트 핸들러
async function loginHandler() {
  let response = await fetch("http://localhost:8080/user/login", {
    method: "POST",
    headers: {
      Accept: "application/json",
      "Content-type": "application/x-www-form-urlencoded",
    },
    body: `email=${user.email}&password=${user.password}`,
  });

  let json = await response.json();
  saveStores(json);

  routerLogic();
}

  function dlgOkHandler() {
    showModal.value = false;
  }

  function saveStores(json) {
    userDetails.id = json.result.id;
    userDetails.email = json.result.email;
    userDetails.nickname = json.result.nickname;
    userDetails.profileSrc = json.result.image;
    userDetails.url = json.result.url;
  }

  function routerLogic() {
    let returnURL = route.query.returnURL;
    if (!userDetails.email) {
      showModal.value = true;
      user.password = "";
    }
    else if (returnURL)
      router.push(returnURL);
    else router.push("/index");
  }

</script>
<template>
  <section class="main">
    <div class="login-border">
      <div class="login-box">
        <router-link to="/index"><img src="/src/assets/images/pofo.svg" class="logo-img" /></router-link>

        <form class="margin-top-15">
          <p class="font-weight-500">이메일</p>
          <input type="text" class="input-text" v-model="user.email" />

          <p class="font-weight-500">비밀번호</p>
          <input type="password" class="input-text" v-model="user.password" autocomplete="off" />
          <button class="btn btn-0 margin-top-8 margin-bottom-5" @click.prevent="loginHandler">
            로그인
          </button>
          <router-link to="/sendlink"><a class="font-size1 margin-top-3 float-right">비밀번호 찾기 ></a></router-link>
        </form>

        <div class="margin-top-20 sub-section">
          <p class="margin-bottom-5 font-weight-500 font-size1 text-center">
            SNS로 간편하게 시작하기
          </p>

          <div class="logos margin-top-1">
            <GoogleLogin :callback="googleLoginHandler" popup-type="TOKEN">
              <img src="/src/assets/images/google_logo.png" class="social-logo" />
            </GoogleLogin>
            <a><img src="/src/assets/images/kakao_logo.png" /></a>
            <a><img src="/src/assets/images/naver_logo.svg" /></a>
          </div>
        </div>

        <div class="margin-top-20">
          <span class="margin-right-22 font-size1">아직 포폴의 회원이 아니세요?</span>
          <router-link to="/signup">
            <span class="font-size1 margin-left-5 underline">
              회원가입 하기
            </span>
          </router-link>
        </div>
      </div>
    </div>
  </section>
  <Modal :show="showModal" @ok="dlgOkHandler" type=1 title="입력값을 확인하세요" />
</template>
<style scoped>
@import url("/src/assets/css/compoment/login.css");

.social-logo:hover {
  cursor: pointer;
}
</style>
