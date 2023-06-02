<script setup>
import { reactive, ref } from "vue";
import { useRouter, useRoute } from "vue-router";
import Modal from "./Modal.vue";

//--------------데이터
let email = ref("");
let showModal = ref(false);
let title = ref("");
let router = useRouter();
let route = useRoute();

async function checkEmail() {
  let response = await fetch(
    `http://localhost:8080/email/checkemail?email=${email.value}`
  );
  let result = await response.text();
  if (result === "ok") {
    showModal.value = true;
    title.value = "이메일이 존재하지 않습니다. 다시 확인해주세요";
  } else {
    let sendLink = await fetch(
      `http://localhost:8080/email/findpwd?email=${email.value}`,
      {
        method: "POST",
      }
    );
    let sendResult = await sendLink.text();
    if (sendResult == "OK") {
      showModal.value = true;
      title.value = "이메일을 발송했습니다. 메일함을 확인해주세요";
    } else {
      showModal.value = true;
      title.value = "이메일 발송에 실패했습니다. 다시 시도해주세요";
    }
  }
}
// async function sendEmailHandler() {
//   let form = documnet.querySelector("#reset-form");

//   let response = await fetch(
//     `http://localhost:8080/email/checkemail${user.email}`
//   );
//   let result = await response.text();
//   if (result === "ok") {
//     router.push("/pwdresetlink");
//   } else {
//     showModal.value = true;
//   }
// }
</script>

<template>
  <!DOCTYPE html>
  <html>
    <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>Document</title>
    </head>
    <body>
      <section class="main">
        <main class="reset-border">
          <h1 class="margin-top-9">
            <img src="/src/assets/images/pofo.svg" class="logo-img" />
          </h1>
          <h2 class="margin-top-9">비밀번호 찾기</h2>
          <p class="font-size1 margin-top-9">가입 시 등록했던 이메일로</p>
          <p class="font-size1 margin-bottom-5">
            비밀번호를 변경할 수 있는 메일을 보내드릴게요.
          </p>
          <form id="reset-form">
            <div class="margin-top-9">
              <input
                class="input-text input-text-placeholder"
                type="email"
                id="email"
                placeholder="이메일 입력"
                name="email"
                autocomplete="off"
                v-model="email"
              />
              <span id="pwd-validation" class="block-red margin-top-2"></span>
              <input
                v-on:click="checkEmail()"
                class="margin-top-10 nbtn pwd-btn"
                type="button"
                value="이메일 전송"
                id="send-btn"
              />
            </div>
          </form>

          <div class="modal-bg d-none">
            <section class="modal-style d-none">
              <div class="margin-top-10">
                <span id="modal-text"></span>
              </div>
              <button class="btn btn-3" type="button" id="cancel">확인</button>
            </section>
          </div>
        </main>
      </section>
    </body>
  </html>
  <Modal :show="showModal" @ok="showModal = false" type="1" :title="title" />
</template>

<style scoped>
@import url("/src/assets/css/compoment/pwd-page.css");
@import url("/src/assets/css/common/reset.css");
@import url("/src/assets/css/common/style.css");
@import url("/src/assets/css/common/util.css");
@import url("/src/assets/css/common/buttons.css");
</style>
