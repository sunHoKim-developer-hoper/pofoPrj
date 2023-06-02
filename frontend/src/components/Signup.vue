<script setup>
import { ref, watch, onUpdated, onMounted, onBeforeMount  } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { useUserDetailsStore } from '../stores/useUserDetailsStore';
import Modal from './Modal.vue';

let email = ref('');
let nickname = ref('');
let pwd = ref('');
let subPwd = ref('');
let showModal = ref(false);

let router = useRouter();
let route = useRoute();
let userDetails = useUserDetailsStore();


let certiNum = ref();
let minutes = ref(2);
let seconds = ref(0);


let buttonText = ref('인증번호 전송');


// 이메일 입력 가능 여부
let isDisabled = ref(false);

let isCountDownVisible = ref(false);

// 인증번호 일치 여부
let isNotEqual = ref(false);
// 인증번호 입력창 보이기 여부
let isVisible = ref(false);
// 이메일 인증 성공 여부
let resultOfCertification = ref(false);


let nickNameCount = ref();
let emailCount = ref();
let emailRegTest = ref(false);
let pwdRegTest = ref('');
let subPwdTest = ref('');

watch(nickname, () => {
    nickNameCount = ref();
})

watch(certiNum, () => {
    isNotEqual.value = false;
})

watch(email, () => {
    buttonText.value = '인증번호 전송';
    emailCount = ref();
    emailRegTest.value = false;
    if(route.query.type === 'oauth'){
        buttonText.value = '인증 완료';
        resultOfCertification.value = true;
    }
})

onBeforeMount(() => {
    if(route.query.type==='oauth'){
        email.value = userDetails.email;
        isDisabled.value = true;
    }
})

async function signupHandler() {
    if(!resultOfCertification.value || !isPassword() || nickNameCount.value===1 || nickname.value=='' || pwd.value !== subPwd.value){
        showModal.value = true;
        return;
    }

    let form = document.querySelector("#form");
    let formData = new FormData(form);
    let response = await fetch("http://localhost:8080/user/signup", {
        method: "POST",
        body: formData
    });
    let result = await response.text();

    if (result == "ok") {
        router.push("/login");
    }
}

function goBack(){
    history.back();
}


async function sendEmail() {
    
    if(!checkEmail()){
        emailRegTest.value = true;
        return;
    }
    // 버튼 전송 중으로 바꾸기
    buttonText.value = '전송 중'
    //전송 중에 전송 버튼 못 누르게 하기
    isDisabled.value = true;

    let response = await fetch(`http://localhost:8080/email/sendemail?email=${email.value}`);
    let result = await response.text();

    if (result) {
        buttonText.value = '전송 완료';
        isVisible.value = true;
        isDisabled.value = false;
    } else {
        buttonText.value = '인증번호 전송'
        emailCount.value = 1;
    }
}

async function checkNum() {
    let response = await fetch(`http://localhost:8080/email/check?certificationnum=${certiNum.value}&email=${email.value}`);
    let result = await response.text();
    if (result == 'ok') {
        buttonText.value = '인증 성공';
        isVisible.value = false;
        resultOfCertification.value = true;
        isDisabled.value = true;
    } else {
        isNotEqual.value = true;
    }
}

async function checkNickname() {
    if(nickname.value=='' || nickname.value== null || nickname.value == undefined)
        return;


    let response = await fetch(`http://localhost:8080/user/nicknamecheck?nickname=${nickname.value}`);
    let result = await response.text();
    if (result == 'ok') {
        nickNameCount.value = 0;
    } else {
        nickNameCount.value = 1;
    }
}

function dlgOkHandler(){
    showModal.value=false;
}

function pwdInput(){

    if(pwd.value.length ==0){
        pwdRegTest.value = '';
        return;
    }

    if(!isPassword())
        pwdRegTest.value = 'no';
    else
        pwdRegTest.value = 'yes';
}

function subPwdInput(){
    
    if(subPwd.value.length ==0){
        subPwdTest.value = '';
        return;
    }

    if(!(pwd.value === subPwd.value))
        subPwdTest.value = 'no';
    else
        subPwdTest.value = 'yes';
}

//이메일 형식 검사
function checkEmail(){
    const emailReg = new RegExp(/^[a-zA-Z0-9+-_.]+@[a-zA-Z0-9-]+\.[a-zA-Z0-9-.]+$/);
	if (emailReg.test(email.value)) 
		return true;
	else
        return false;
}

//비밀번호 형식 검사
function isPassword() {
	let regExp = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
	return regExp.test(pwd.value);
}

</script>

<template>
    <div class="black-bg">
        <div class="white-bg">
            <header class="">
                <router-link to="/index"><img src="/src/assets/images/pofo.svg" class="logo-img"></router-link>
            </header>
            <form id="form">
                <div class="flex-colum line-text">이메일로 가입하기</div>
                <div class="margin-top-5">
                    <h6 class="margin-top-10">* 이메일주소</h6>
                    <input type="text" class="security-input-text" v-model="email" @input="" autocomplete="off" name="email"
                        :readonly="isDisabled">
                    <button class="security-btn margin-top-2" @click.prevent="sendEmail" :disabled="isDisabled">{{ buttonText }}</button>
                    <span class="margin-top-1 red" v-if="emailCount == 1">중복된 이메일입니다.</span>
                    <span class="margin-top-1 red" v-if="emailRegTest">유효하지 않은 이메일 형식입니다.</span>
                    <span class="margin-top-1 green" v-if="resultOfCertification">이메일 인증에 성공하셨습니다.</span>

                    <div v-if="isVisible">
                        <h6 class="margin-top-5">이메일 인증</h6>
                        <input type="text" class="security-input-text" v-model="certiNum">
                        <button class="security-btn margin-top-2" @click.prevent="checkNum">인증하기</button>
                        <span class="margin-top-1 red" v-if="isNotEqual">인증번호가 다릅니다.</span>
                    </div>

                    <h6 class="margin-top-5">* 닉네임</h6>
                    <input type="text" class="security-input-text" v-model="nickname" autocomplete="off" name="nickname">
                    <button class="security-btn margin-top-2" @click.prevent="checkNickname">중복 확인</button>
                    <span class="margin-top-1 red" v-if="nickNameCount == 1">닉네임이 중복됩니다.</span>
                    <span class="margin-top-1 green" v-if="nickNameCount == 0">사용가능한 닉네임입니다.</span>
                </div>

                <div class="margin-top-5">
                    
                    <h6 class="margin-top-5">* 비밀번호</h6>
                    <input type="password" class="input-text1" 
                        placeholder="비밀번호(숫자,영문,특수문자 포함 8~16자리)" autocomplete="off" name="pwd" @input="pwdInput" v-model="pwd">
                    <span class="margin-top-1 red" v-if="pwdRegTest == 'no'">유효하지 않은 비밀번호입니다.</span>
                    <span class="margin-top-1 green" v-if="pwdRegTest == 'yes'">유효한 비밀번호입니다.</span>
                    
                    <h6 class="margin-top-5">비밀번호확인</h6>
                    <input type="password" class="input-text1" 
                        placeholder="비밀번호(숫자,영문,특수문자 포함 8~16자리)" autocomplete="off" @input="subPwdInput" v-model="subPwd">
                    <span class="margin-top-1 red" v-if="subPwdTest == 'no'">비밀번호가 일치하지 않습니다.</span>
                    <span class="margin-top-1 green" v-if="subPwdTest == 'yes'">비밀번호가 일치합니다.</span>

                    <h6 class="margin-top-5">URL 추가</h6>
                    <input type="text" class="input-text1" name="url">

                </div>
            </form>
            <div class="flex-row-between1 margin-top-10">
                <button class="btn-back" @click.prevent="goBack">이전화면</button>
                <button class="btn-next" @click.prevent="signupHandler">회원가입</button>
            </div>
        </div>
    </div>
        <Modal :show="showModal" @ok="dlgOkHandler" type=1 title="입력값을 확인하세요"/>
</template>
<style scoped>
@import url("/src/assets/css/compoment/signup.css");


</style>