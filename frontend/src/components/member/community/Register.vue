<script setup>
import Header from '../../Header.vue'
import { onMounted, reactive, ref } from 'vue';
import { useUserDetailsStore } from '../../../stores/useUserDetailsStore';
import { useRouter, useRoute } from 'vue-router';

let userDetails = useUserDetailsStore();
let router = useRouter();
let showLoaing = ref(false);

// --- Variables ---------------------------------------
let community = reactive({
    title: "",
    onlineType: true,
    locationInfo: "",
    period: "",
    teamSize: null,
    thumbnail: "",
});

let fileInputRef = ref(null);
let imgRef = ref(null);
let isUploaded = ref(false);


// --- Event Handlers ----------------------------------
async function registerHandler() {
    showLoaing.value = true;
    const url = new URL("http://localhost:8080/members/community/register");
    let form = document.querySelector("#form");
    const formData = new FormData(form);
    formData.append("memberId", userDetails.id); 
    formData.append("onlineType", community.onlineType); 

    let response = await fetch(url, {
        method: "POST",
        headers: {
            "Accept": "application/json",
        },
        body: formData
    })

    let result = await response.text();


    if (result == "true") {
        showLoaing.value = false;
        router.push("/community/list");
    } else {
        showLoaing.value = false;
    }
}

function imageBoxClickHandler(){
    let fileInput = fileInputRef.value;
    
    // fileInput.click(); // 이 방법 보다는..?
    const event = new MouseEvent("click", {
    view: window,
        bubbles: true,
        cancelable: true,
    });

    fileInput.dispatchEvent(event);
}

function btnHandler(){
    let fileInput = fileInputRef.value;
    
    // fileInput.click(); // 이 방법 보다는 아래의 방법으로
    const event = new MouseEvent("click", {
    view: window,
        bubbles: true,
        cancelable: true,
    });

    fileInput.dispatchEvent(event);
}

function fileInputHandler(e) {
    const file = fileInputRef.value.files[0];

    if (file) {
        const reader = new FileReader();

        reader.onload = function (event) {
            imgRef.value.src = event.target.result;
            isUploaded.value = true;
        };

        reader.readAsDataURL(file);

    } else {
        imgRef.value = null;
    }

}

function resetHandler(){
    community.title = "";
    community.onlineType = true;
    community.locationInfo = "";
    community.period = "";
    community.teamSize = null;
    community.thumbnail = "";
    imgRef.value.src = "/src/assets/images/img.png";
    isUploaded.value = false;
}

</script>
<template>
    <Header></Header>
    <div class="container">
        <div class="border-blue"></div>
        <section class="team-c-title margin-top-2">
            <h1 class="team-title margin-top-7">팀 개설하기 안내</h1>
            
            <p class="text margin-top-5">새로운 주제로 팀 프로젝트를 진행하고 싶으신가요 ? <br>
                <br>

                <strong>프로젝트 목표!</strong> 
                <img class="team-img" src="/src/assets/images/diversity.png" alt="">
                <br>
                이 목표는 당신의 팀이 어떤 프로젝트를 하고 싶은지,<br>
                그리고 그 목표를 달성하기 위해 필요한 사람들을 모으는 것입니다.<br>
                목표를 설정하면 팀을 개설하고 활동을 계획하는 데 도움이 됩니다.<br>
                아래 양식에 맞게 입력을 해주시면 새로운 팀 프로젝트가 진행됩니다!<br>
                자세한 팀원 모집은 알림과 채팅 기능을 이용해보세요!
                <img class="noti" src="/src/assets/images/notification.png" alt="">
            </p>
        </section>
        <form class="team-c-form" action="" method="post" id="form">
            <main class="team-c-main margin-top-1">
                <div class="first-img-box">
                    <div class="img-box" @click="imageBoxClickHandler">
                        <div 
                            class="info"
                            v-if="isUploaded == false"
                            >
                            <span >이미지를 업로드 하세요.</span>
                            <span >JPEG,JPG,GIF 이미지파일</span>
                        </div>
                        <div>
                            <img class="img-ref" :class="{'d-none': isUploaded}" src="/src/assets/images/img.png" ref="imgRef" />
                            <img :class="{'d-none': !isUploaded}" src="" ref="imgRef" />
                        </div>
                    </div>
                    <input type="file" class="d-none" ref="fileInputRef" @input="fileInputHandler" name="image">
                    <button class="modal-submit-btn thum-btn" @click.prevent="btnHandler" type="button">이미지업로드</button>
                </div>
                <div class="text-box margin-top-3">
                    <h2>제목</h2>
                    <input class="team-c-input" type="text" placeholder="내 답변" v-model="community.title" name="title">
                </div>
                <div class="text-box margin-top-3">
                    <h2>닉네임</h2>
                    <input class="team-c-input nickname" type="text" :placeholder="userDetails.nickname" readonly>
                </div>
                <div class="button-box margin-top-3">
                    <h2>온·오프라인</h2>
                    <div class="btn-box margin-top-5">
                        <button class="btn btn-1 margin-right-10" :class="{'focus': community.onlineType}" @click.prevent="community.onlineType = true">ON-LINE</button>
                        <button class="btn btn-1" :class="{'focus': !community.onlineType}" @click.prevent="community.onlineType = false">OFF-LINE</button>
                    </div>
                </div>
                <div class="text-box margin-top-3">
                    <h2>장소</h2>
                    <input class="team-c-input" type="text" placeholder="내 답변" v-model="community.locationInfo" name="locationInfo">
                </div>
                <div class="text-box margin-top-3">
                    <h2>기간</h2>
                    <input class="team-c-input" type="text" placeholder="내 답변" v-model="community.period" name="period">
                </div>
                <div class="text-box margin-top-3">
                    <h2>모집인원</h2>
                    <input class="team-c-input" type="text" placeholder="내 답변" v-model="community.teamSize" name="teamSize">
                </div>
                <div class="submit-box margin-top-2">
                    <button class="reset" @click.prevent="resetHandler" >양식지우기</button>
                    <button class="submit" type="submit" @click.prevent="registerHandler">등록</button>
                </div>
            </main>
        </form>
    </div>
    <div v-show="showLoaing" class="loading-screen">
        <div  class=" loading-white-bg loader"></div>
    </div>
</template>
<style scoped>
@import url("/src/assets/css/compoment/team-register.css");

.team-c-input::placeholder {
    color: rgb(195, 190, 190); /* 색상을 변경하고자 하는 값으로 대체하세요 */
}

.nickname::placeholder {
    color: rgb(60, 59, 59); 
}

.img-box .img-ref {

    object-fit: none;
    margin-top: -40px;
  }

  .loader {
  border: 16px solid #f3f3f3; /* Light grey */
  border-top: 16px solid #7404FA;
  border-radius: 50%;
  width: 90px;
  height: 90px;
  animation: spin 1.5s linear infinite;
}

@keyframes spin {
  0% { transform: rotate(0deg); }
  100% { transform: rotate(360deg); }
}


</style>