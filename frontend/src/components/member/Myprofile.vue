<script setup>
import Header from '../Header.vue';
import { onMounted, reactive, ref, watch } from 'vue';
import { useRoute } from 'vue-router';
import { useUserDetailsStore } from '../../stores/useUserDetailsStore';
import Modal from '../Modal.vue';
import FollowModal from '../FollowModal.vue';

let userDetails = useUserDetailsStore();
let route = useRoute();

//현재 클릭되어있는 TAB
let current = ref(0);
let isThis = ref(false);

//팔로우 모달관련
let showFollowModal = ref(false);
let followModalTitle = ref('');
let followList = reactive([]);
let modalType = ref();
let followingList = null;

let showQuitModal = ref(false);

let showValidModal = ref(false);

let src = ref();
let selected = ref(false);

//사용자가 정보 수정에서 파일을 선택했을 시 이미지 정보를 담을 변수
let fileInfo = null;
let nickNameCount = ref();
let nickname = ref(userDetails.nickname);
let url = ref(userDetails.url);

let pwd = ref(null);
let subPwd = ref(null);

let pwdRegTest = ref();
let subPwdTest = ref();

let isModifyNickname = ref(false);
let isModifyPwd = ref(false);

let showLoaing = ref(false);

let model = reactive({
    myInfo: {},
    list: [[], [], [], []],
    currentList: [],
    activities: {}
})


onMounted(() => {
    load();
});

async function load() {
    let param = route.params.id;
    let response = await fetch(`http://localhost:8080/members/myprofile/${param}`);
    let json = await response.json();
    model.myInfo = json.member;
    model.list[0] = json.works;
    model.list[1] = json.likes;
    model.list[2] = json.collections;
    model.list[3] = json.communities;
    model.currentList = json.works;
    model.activities = json.activities;
}

function selectImage(e) {
    let fileSelector = e.target.nextElementSibling;
    fileSelector.click();
}

function changeImage(e) {
    selected.value = true;
    fileInfo = e.target.files[0];
    src.value = URL.createObjectURL(fileInfo);
}

function clickWork() {
    current.value = 0;
    model.currentList = model.list[0];
}

function clickLikes() {
    current.value = 1;
    model.currentList = model.list[1];
}

function clickCollections() {
    current.value = 2;
    model.currentList = model.list[2];
}

function clickCommunities() {
    current.value = 3;
    model.currentList = model.list[3];
}

watch(nickname, () => {
    nickNameCount = ref();
})

// ------------------모달 관련 이벤트------------------

function dlgOkHandler() {
    showQuitModal.value = false;
    showValidModal.value = false;
    showFollowModal.value = false;
}

//회원 탈퇴 여부 물어보는 모달
function showModalHandler() {
    showQuitModal.value = true;
}


//수정 모달관련 이벤트 핸들러
function profileUpdate() {
    isThis.value = true;
}

function closeModifyModal() {
    isThis.value = false;
}

//팔로우 모달관련 이벤트 핸들러
function followModal(e, title, type) {
    followingList = reactive([]);
    followModalTitle.value = title;
    modalType.value = type;
    fetch(`http://localhost:8080/follow/${userDetails.id}?type=${modalType.value}`)
        .then(response => response.json())
        .then(json => {
            followList = json.list;
            followingList = json.counts;
        })
        .then(() => {
            showFollowModal.value = true;
        })
}

//팔로우 취소
async function cancleFollow(id) {
    let profileId = id;
    let response = await fetch("http://localhost:8080/profile/follow", {
        method: "DELETE",
        headers: {
            "Content-type": "application/x-www-form-urlencoded"
        },
        body: `requesterId=${userDetails.id}&requestedId=${profileId}`
    });
    let result = await response.text();

    if (result === 'ok') {
        showFollowModal.value = false;
        await load();
        followModal(null, '팔로잉', 0);
    }
}

async function follow(id) {
    let profileId = id;
    let response = await fetch("http://localhost:8080/profile/follow", {
        method: "POST",
        headers: {
            "Content-type": "application/x-www-form-urlencoded"
        },
        body: `requesterId=${userDetails.id}&requestedId=${profileId}`
    });
    let result = await response.text();
    if (result === 'ok') {
        showFollowModal.value = false;
        await load();
        followModal(null, '팔로우', 1);
    }
}
// --------------------------------------------------

async function modifyInfo() {
    if ((isModifyNickname.value && nickNameCount.value === 1) || (isModifyNickname.value && nickname.value == '')) {
        showValidModal.value = true;
        return;
    }

    if ((isModifyPwd.value && !isPassword()) || (isModifyPwd.value && pwd.value !== subPwd.value)) {
        showValidModal.value = true;
        return;
    }

    showLoaing.value = true;
    isThis.value = false;

    let formData = new FormData();
    if (fileInfo)
        formData.append("profile", fileInfo);
    if (nickname.value)
        formData.append("nickname", nickname.value);
    if (pwd.value)
        formData.append("pwd", pwd.value);
    if (userDetails.profileSrc)
        formData.append("image", userDetails.profileSrc);

    formData.append("id", userDetails.id);
    let response = await fetch("http://localhost:8080/members/modify", {
        method: "PUT",
        headers: {
            "Accept": "application/json",
        },
        body: formData
    });
    let result = await response.text();
    
    fetch(`http://localhost:8080/user/image/${userDetails.id}`)
        .then(response => response.text())
        .then(result =>{
            userDetails.profileSrc = result;
            console.log(userDetails.profileSrc);
    });
    
    if (result) {
        showLoaing.value = false;
        await load();
    }
}

async function checkNickname() {
    if (nickname.value == '' || nickname.value == null || nickname.value == undefined)
        return;

    let response = await fetch(`http://localhost:8080/user/nicknamecheck?nickname=${nickname.value}`);
    let result = await response.text();
    if (result == 'ok') {
        nickNameCount.value = 0;
    } else {
        nickNameCount.value = 1;
    }
}

function modifyNickname() {
    isModifyNickname.value = !isModifyNickname.value;
}

function modifyPwd() {
    isModifyPwd.value = !isModifyPwd.value;
}


function pwdInput() {

    if (pwd.value.length == 0) {
        pwdRegTest.value = '';
        return;
    }

    if (!isPassword())
        pwdRegTest.value = 'no';
    else
        pwdRegTest.value = 'yes';
}

function subPwdInput() {

    if (subPwd.value.length == 0) {
        subPwd.value = '';
        return;
    }

    if (!(pwd.value === subPwd.value))
        subPwdTest.value = 'no';
    else
        subPwdTest.value = 'yes';
}

function isPassword() {
    let regExp = /^(?=.*[a-zA-z])(?=.*[0-9])(?=.*[$`~!@$!%*#^?&\\(\\)\-_=+]).{8,16}$/;
    return regExp.test(pwd.value);
}

</script>
<template>
    <Header></Header>
    <main class="container">

        <section class="margin-right-5 profile">
            <h1 class="d-none">왼편 프로필 창</h1>
            <div class="profile-info">
                <img class="profile-img" src="/src/assets/images/proflie.svg" alt="마이프로필"
                    v-if="userDetails.profileSrc == null" @click.prevent="profileUpdate" />
                <img :src="'http://localhost:8080/profileImage/' + userDetails.profileSrc" class="profile-img" v-else
                    @click.prevent="profileUpdate" />
                <div class="nickname">
                    {{ model.myInfo.nickname }}
                </div>
                <a :href="model.myInfo.url" class="url" :hash="false">{{ model.myInfo.url }}</a>
                <div>
                    <button class="btn btn-0 font-size-15 btn-height" @click.prevent="profileUpdate">프로필 편집</button>
                    <button class="d-none">팔로우</button>
                </div>
            </div>

            <div class="margin-top-10 margin-bottom-5 font-size-14">
                활동 정보
            </div>

            <div class="activity-boxes">
                <div>
                    <div class="margin-bottom-5 font-size-15 bold">{{ model.activities.hited }}</div>
                    <div class="font-size-14 font-gray">작업 보기</div>
                </div>
                <div>
                    <div class="margin-bottom-5 font-size-15 bold">{{ model.activities.liked }}</div>
                    <div class="font-size-14 font-gray">좋아요 받음</div>
                </div>
                <div>
                    <div class="margin-bottom-5 font-size-15s bold">{{ model.activities.collected }}</div>
                    <div class="font-size-14 font-gray">컬렉션 북마크</div>
                </div>
                <div>
                    <div class="margin-bottom-5 font-size-15 bold hover" @click.prevent="followModal($event, '팔로잉', 0)">{{ model.activities.following }}</div>
                    <div class="font-size-14 font-gray hover" @click.prevent="followModal($event, '팔로잉', 0)">팔로잉</div>
                </div>
                <div>
                    <div class="margin-bottom-5 font-size-15 bold hover" @click.prevent="followModal($event, '팔로워', 1)">{{ model.activities.follower }}</div>
                    <div class="font-size-14 font-gray hover" @click.prevent="followModal($event, '팔로워', 1)">팔로워</div>
                </div>
            </div>
        </section>

        <section class="profile-content">
            <div class="tab-container">
                <div class="items">
                    <div @click="clickWork">
                        <span :class="{ 'non-seleted': current != 0, 'selected': current == 0 }">작업</span>
                        <span class="num" :class="{ 'num-non-selected': current != 0 }">{{ model.list[0].length }}</span>
                    </div>
                    <div @click="clickLikes">
                        <span :class="{ 'non-seleted': current != 1, 'selected': current == 1 }">좋아요</span>
                        <span class="num" :class="{ 'num-non-selected': current != 1 }">{{ model.list[1].length }}</span>
                    </div>
                    <div @click="clickCollections">
                        <span :class="{ 'non-seleted': current != 2, 'selected': current == 2 }">컬렉션</span>
                        <span class="num" :class="{ 'num-non-selected': current != 2 }">{{ model.list[2].length }}</span>
                    </div>
                    <div @click="clickCommunities">
                        <span :class="{ 'non-seleted': current != 3, 'selected': current == 3 }">커뮤니티</span>
                        <span class="num" :class="{ 'num-non-selected': current != 3 }">{{ model.list[3].length }}</span>
                    </div>
                </div>
            </div>

            <div class="portfolio-lists" v-if="current < 3">
                <router-link :to="'/pofo/' + pofo.id" v-for="pofo in model.currentList">
                    <div class="thumbnail" :data-title="pofo.title">
                        <span>
                            <img :src="'http://localhost:8080/portfolio/thumbnails/' + pofo.thumbnail">
                        </span>
                    </div>
                </router-link>
            </div>

            <div class="portfolio-lists" v-else>
                <router-link :to="'/community/' + commu.id" v-for="commu in model.currentList">
                    <div class="thumbnail" :data-title="commu.title">
                        <span>
                            <img :src="'http://localhost:8080/communityImage/' + commu.thumbnail">
                        </span>
                    </div>
                </router-link>
            </div>
        </section>
    </main>

    <!--  개인 정보 수정 모달창  -->
    <div class="black-bg" v-if="isThis">
        <div class="white-bg">
            <h4 class="d-inline">기본정보</h4><img class="xMark hover" @click="closeModifyModal"
                src="/src/assets/images/xMark.png" />
            <div class="center">
                <div class="margin-top-5 profile-select">
                    <img class="profile-img" src="/src/assets/images/proflie.svg" alt="마이프로필"
                        v-if="userDetails.profileSrc == null && !selected" @click.prevent="selectImage" />
                    <img :src="'http://localhost:8080/profileImage/' + userDetails.profileSrc" class="profile-img"
                        v-else-if="userDetails.profileSrc != null && !selected" @click="selectImage" />
                    <img :src=src class="profile-img" v-else @click="selectImage" />
                    <input type="file" class="d-none" @input="changeImage" accept="jpg,gif,png" />
                </div>
            </div>
            <div class="flex-colum">
                <div class="margin-top-4">
                    <span> 닉네임</span>
                    <input class="input-text2" type="text" autocomplete="off" v-model="nickname"
                        :readonly="!isModifyNickname">
                    <button class="nick-btn" @click.prevent="modifyNickname" v-if="!isModifyNickname">닉네임 수정</button>
                    <button class="nick-btn" @click.prevent="checkNickname" v-else>중복 확인</button>
                    <span class="red left-padding-3" v-if="nickNameCount == 1">닉네임이 중복됩니다.</span>
                    <span class="green left-padding-3" v-if="nickNameCount == 0">사용가능한 닉네임입니다.</span>
                </div>

                <div class="margin-top-4">
                    <span> 비밀번호</span>
                    <input class="input-text2" type="password" autocomplete="off" v-model="pwd" @input="pwdInput"
                        placeholder="비밀번호(숫자,영문,특수문자 포함 8~16자리)" :readonly="!isModifyPwd">
                    <button class="nick-btn" @click.prevent="modifyPwd" v-if="!isModifyPwd">비밀번호 수정</button>
                    <span class="red" v-if="pwdRegTest == 'no'">유효하지 않은 비밀번호입니다.</span>
                    <span class="green" v-if="pwdRegTest == 'yes'">유효한 비밀번호입니다.</span>
                </div>

                <div class="margin-top-4" v-if="isModifyPwd">
                    <span> 비밀번호 확인</span>
                    <input class="input-text2" type="password" autocomplete="off" v-model="subPwd" @input="subPwdInput">
                    <span class="red" v-if="subPwdTest == 'no'">비밀번호가 일치하지 않습니다.</span>
                    <span class="green" v-if="subPwdTest == 'yes'">비밀번호가 일치합니다.</span>
                </div>

                <div class="margin-top-4">
                    <span> URL주소 변경</span>
                    <input class="input-text2" type="text" autocomplete="off" v-model="url">
                </div>
            </div>
            <div class="margin-top-8 flex-row-between1">
                <button class="btn-bye-bye" @click.prevent="showModalHandler">회원탈퇴</button>
                <button class="btn-updateprofile" @click.prevent="modifyInfo">수정하기</button>
            </div>
        </div>
    </div>
    <Modal :show="showQuitModal" @ok="dlgOkHandler" type=2 title="진심으로 탈퇴요?" />
    <Modal :show="showValidModal" @ok="dlgOkHandler" type=1 title="입력값을 확인하세요" />

    <FollowModal :show="showFollowModal" :title="followModalTitle" :list="followList" :type="modalType"
        :currentFollowingList="followingList" v-if="showFollowModal" @ok="dlgOkHandler" @cancleFollow="cancleFollow"
        @follow="follow" />

    <!-- 로딩 gif -->
    <div v-show="showLoaing" class="loading-screen">
    <div  class=" loading-white-bg loader"></div>
  </div>
</template>
<style scoped>
@import url("/src/assets/css/compoment/profile.css");
@import url("/src/assets/css/compoment/profileupdate.css");

.thumbnail:hover::before {
    content: attr(data-title);
    position: absolute;
    top: 0;
    left: 0;
    right: 0;
    bottom: 0;
    background-color: rgba(0, 0, 0, 0.5);
    z-index: 1;
    display: flex;
    color: #fff;

    align-items: flex-end;
    justify-content: flex-start;
    font-size: 1rem;
    padding-left: 5px;
    padding-bottom: 5px;

    white-space: nowrap;
    overflow: hidden;
    text-overflow: ellipsis;
}

.thumbnail:hover img {
    filter: brightness(80%);
}

.xMark:hover {
    cursor: pointer;
}

.xMark {
    float: right;
    width: 20px;
    height: 20px;
}

.d-inline {
    display: inline;
}

.nick-btn {
    margin-top: 2%;
    padding: 12px 3px;
    width: 95px;
    background-color: #000000;
    border-radius: 4px;
    /* 외곽선 없애기 */
    border: none;
    color: #fff;
    font-family: 'Noto Sans KR', sans-serif;
}

span.red {
    display: inline-block;
    font-size: 10px;
    color: red;
}

span.green {
    display: inline-block;
    font-size: 10px;
    color: green;
}

.left-padding-3 {
    padding-left: 3%;
}

input:read-only {
    color: gray;
}

.hover:hover {
    cursor: pointer;
}



/* 로딩 css추가  */
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