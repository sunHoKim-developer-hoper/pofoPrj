<script setup>
import { reactive, ref, onMounted } from "vue";
import { useRouter, useRoute } from "vue-router";
import Modal from "./Modal.vue";

//--------------데이터
let email = ref("");
let title = ref("");
let showModal = ref(false);
let pwd = ref("");
let subPwd = ref("");
let subPwdModiTest = ref("");
let pwdModiTest = ref("");

let router = useRouter();
let route = useRoute();

onMounted(() => {
  async function checkuuid() {
    let query = route.query.uuid;
    let response = await fetch(
      `http://localhost:8080/email/uuidcheck?uuid=${query}`
    );
    let result = await response.text();
    if (result == "ok") {
      router.push("/pwdreset?uuid=" + query);
    } else {
      router.push("/error404");
    }
  }
  checkuuid();
});

function pwdInput() {
  if (pwd.value.length == 0) {
    pwdModiTest.value = "";
    return;
  }

  if (!isPassword()) pwdModiTest.value = "no";
  else pwdModiTest.value = "yes";
}
//비밀번호 형식 검사
function isPassword() {
  let regExp =
    /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
  return regExp.test(pwd.value);
}

function subPwdInput() {
  if (subPwd.value.length == 0) {
    subPwdModiTest.value = "";
    return;
  }

  if (!(pwd.value === subPwd.value)) subPwdModiTest.value = "no";
  else subPwdModiTest.value = "yes";
}

async function modifiedHandler() {
  if (!isPassword() || pwd.value !== subPwd.value) {
    showModal.value = true;
    title.value = "비밀번호를 다시 확인해주세요";
    return;
  }
  let query = route.query.uuid;
  let form = document.querySelector("#reset-form");
  let formData = new FormData(form);
  let response = await fetch(
    `http://localhost:8080/email/modifypwd?uuid=${query}`,
    {
      method: "POST",
      body: formData,
    }
  );
  let result = await response.text();
  if (result == "ok") {
    router.push("/login");
  } else {
    showModal.value = true;
    title.value = "비밀번호 변경에 실패했습니다. 다시 시도해주세요";
  }
}

// if (result === "ok") {
//   showModal.value = true;
//   title.value = "이메일이 존재하지 않습니다. 다시 확인해주세요";
// } else {
//   let sendLink = await fetch(
//     `http://localhost:8080/email/findpwd?email=${email.value}`,
//     {
//       method: "POST",
//     }
//   );
// }
</script>

<template>
  <!DOCTYPE html>
  <html>
    <head>
      <meta charset="UTF-8" />
      <meta http-equiv="X-UA-Compatible" content="IE=edge" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>ResetPasswordPage</title>
    </head>

    <body>
      <section class="main">
        <main class="reset-border">
          <h1 class="margin-top-9">
            <img src="/src/assets/images/pofo.svg" class="logo-img" />
          </h1>
          <h2 class="margin-top-9">비밀번호 변경</h2>

          <form id="reset-form" style="display: inline-grid">
            <div class="margin-top-9">
              <input
                class="input-text input-text-placeholder"
                type="password"
                placeholder="비밀번호(숫자,영문,특수문자 포함 8~16자리)"
                @input="pwdInput"
                name="pwd"
                autocomplete="off"
                v-model="pwd"
              />
              <br />
              <span class="margin-top-1 red" v-if="pwdModiTest == 'no'"
                >유효하지 않은 비밀번호입니다.</span
              ><span class="margin-top-1 green" v-if="pwdModiTest == 'yes'"
                >유효한 비밀번호입니다.</span
              >
              <span id="pwd-validation" class="block-red margin-top-2"></span>
            </div>

            <input
              class="input-text input-text-placeholder margin-top-8"
              name="subPwd"
              type="password"
              id="password-check"
              placeholder="비밀번호 확인"
              autocomplete="off"
              @input="subPwdInput"
              v-model="subPwd"
            />
            <br />
            <span class="margin-top-1 red" v-if="subPwdModiTest == 'no'"
              >비밀번호가 일치하지 않습니다.</span
            >
            <span class="margin-top-1 green" v-if="subPwdModiTest == 'yes'"
              >비밀번호가 일치합니다.</span
            >
            <span id="pwd-certification" class="block-red margin-top-2"></span>

            <input
              class="margin-top-10 nbtn pwd-btn"
              type="button"
              value="비밀번호 변경"
              id="send-btn"
              @click.prevent="modifiedHandler"
            />
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
@import url("/src/assets/css/compoment/signup.css");
</style>
