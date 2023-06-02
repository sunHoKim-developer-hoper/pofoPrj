<template>
  <div
    class="screen"
    :class="{ 'd-none': !show }"
  >
    <div class="white-bg">
      <div class="line-text">
        <h5>{{ title }}</h5>
      </div>
      <div
        class="flex-row-between1 margin-top-20"
        v-if="type === '0'"
      >
        <router-link :to="url">
          <button class="check-btn">
            확인
          </button>
        </router-link>
        <button
          class="check-btn"
          @click.prevent="$emit('ok')"
        >
          취소
        </button>
      </div>

      <div
        class="center margin-top-20"
        v-if="type === '1'"
      >
        <button
          class="check-btn"
          @click.prevent="$emit('ok')"
        >
          확인
        </button>
      </div>

      <div
        class="flex-row-between1 margin-top-20"
        v-if="type === '2'"
      >
        <button
          class="check-btn"
          @click="byeByeFunction"
        >
          탈퇴
        </button>
        <button
          class="check-btn"
          @click.prevent="$emit('ok')"
        >
          취소
        </button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { useRoute } from "vue-router";
import { useUserDetailsStore } from "../stores/useUserDetailsStore";

let props = defineProps({
  title: "",
  show: false,
  type: 0,
});
let userDetails = useUserDetailsStore();
let route = useRoute();
let returnURL = route.path;
let url = `/login?returnURL=${returnURL}`;

async function byeByeFunction() {
  await fetch("http://localhost:8080/users/bye", {
    method: "DELETE",
    headers: {
      "Content-type": "application/x-www-form-urlencoded",
    },
    body: `id=${userDetails.id}`,
  });
  userDetails.logout();
}
</script>
<style scoped>
.screen {
  background-color: rgba(0, 0, 0, 0.8);
  left: 0;
  top: 0;
  width: 100vw;
  height: 100vh;
  position: fixed;
  display: flex;
  align-items: center;
  justify-content: center;
}

.white-bg {
  position: absolute;
  top: 30%;
  left: 42%;
  height: 1200;
  width: 320px;
  padding: 40px 48px;
  border: 1px solid rgb(240, 245, 245);
  background: rgb(255, 255, 255);
  border-radius: 4px;
  box-sizing: border-box;
}

.느낌표 {
  width: 40px;
  height: 40px;
}

.이미지가운데 {
  text-align: center;
}

.flex-row-between1 {
  display: flex;
  justify-content: space-between;
  width: 100%;
}

.center {
  text-align: center;
}

.flex-column {
  -webkit-box-orient: vertical !important;
  -webkit-box-direction: normal !important;
  -ms-flex-direction: column !important;
  flex-direction: column !important;
}

.line-text {
  font-weight: 400;
  font-size: 16px;
  line-height: 16px;
  letter-spacing: -0.25px;
  text-align: center;
  margin-top: 10%;
}

.d-none {
  display: none !important;
}

.check-btn {
  padding: 6px 25px;
  background-color: #000000;
  border-radius: 4px;
  /* 외곽선 없애기 */
  border: none;
  color: #fff;
  font-family: "Noto Sans KR", sans-serif;
}

.btn-back {
  padding: 6px 25px;
  background-color: #ffffff;
  border-radius: 4px;

  /* 외곽선 없애기 */
  border: 1px solid #000000;
  color: #000000;
  font-family: "Noto Sans KR", sans-serif;
}
</style>