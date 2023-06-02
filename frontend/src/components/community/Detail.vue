<script setup>
import { reactive, onMounted, ref, watch } from 'vue';
import Header from '../Header.vue';
import { useRoute, useRouter } from 'vue-router';
import { useUserDetailsStore } from '../../stores/useUserDetailsStore';
import Modal from '../Modal.vue';


// --- Variables ---------------------------------------
let data = reactive({
    community: {}
});

let userDetails = useUserDetailsStore();
let route = useRoute();
let router = useRouter();

let showModal = ref(false);

let isApplied = ref(false);

let teamInfo = reactive({});
//let isApplying = ref(false);

let btnText = ref('');

// --- Life Cycles -------------------------------------
onMounted(async () => {
    await fetchCommunity();
    await getCommunityTeamInfo();
    if(userDetails.id != null) // 로그인된 경우만 확인함
        checkApplicationStatus();
    else
        btnText.value = "신청하기";
})
watch(() => [isApplied.value], checkApplicationStatus); // 변수가 변경될 때마다 함수 실행


// --- Event Handlers ----------------------------------
async function fetchCommunity() {
    let param = route.params.id;
    const url = new URL(`http://localhost:8080/community/${param}`);

    let response = await fetch(url);
    let json = await response.json();
    data.community = json.community;
}

function nicknameClickHandler(e, memberId) {
    if (memberId === userDetails.id)
        router.push("/member/profile/" + memberId);
    else
        router.push("/profile/" + memberId);
}

// 모달창 끄기
function dlgOkHandler(){
    showModal.value=false;
    showFollowModal.value = false;
}

// 신청하기
async function applyBtnClickHandler(){
    if(!userDetails.isAuthenticated){
        showModal.value = true;
    }
    
    if(isApplied.value) { // 신청한적 없을 경우 신청됨
        await fetch("http://localhost:8080/community/apply", {
            method: "POST",
            headers: {
                "Content-type": "application/x-www-form-urlencoded"
            },
            body: `typeId=4&fromMemberId=${userDetails.id}&toMemberId=${data.community.memberId}&communityId=${route.params.id}`
        });
        isApplied.value = !isApplied.value;
    } else if(!isApplied.value){ // 신청한 상태의 경우 취소됨
        await fetch("http://localhost:8080/community/cancle", {
            method: "DELETE",
            headers: {
                "Content-type": "application/x-www-form-urlencoded"
            },
            body: `typeId=4&fromMemberId=${userDetails.id}&toMemberId=${data.community.memberId}&communityId=${route.params.id}`
        });
        isApplied.value = !isApplied.value;
    }
}

// 팀 신청 확인
async function checkApplicationStatus() {
    let response = await fetch("http://localhost:8080/community/getApplicationStatus", {
        method: "POST",
        headers: {
            "Content-type": "application/x-www-form-urlencoded"
        },
        body: `typeId=4&fromMemberId=${userDetails.id}&toMemberId=${data.community.memberId}&communityId=${route.params.id}`
    });

    let result = await response.text();    

    if (result === "false") {
        isApplied.value = true;
        btnText.value = '신청하기';
    } else {
        isApplied.value = false;
        btnText.value = '신청됨';
    }
}

async function getCommunityTeamInfo(){
    let response = await fetch(`http://localhost:8080/community/getcommunityteaminfo?communityId=${route.params.id}&memberId=${userDetails.id}`);
    let json = await response.json();
    teamInfo = json;
}

async function closeHandler(){
    let response = await fetch(`http://localhost:8080/community/close?communityId=${route.params.id}`);
    let result = await response.text();
    if(result)
        location.reload();
}

</script>

<template>
    <Header />
    <main>
        <div class="thumbnail-box">
            <img loading="lazy" class="css-l68de9 e5kxa4l0"
                :src="'http://localhost:8080/communityImage/' + data.community.thumbnail">
        </div>

        <div class="CodeBlock_block__3Yr6P css-3y3f20">
            <div class="contents-form-relative">

                <div class="contens-title-box">
                    <div class="contents-title-text">{{ data.community.title }}</div>
                </div>
                <div class="contents-status-box">
                    <div class="contents-status-1"  v-if="data.community.closeFlag==1">모집 마감</div>
                    <div class="contents-status-1"  v-else>모집 중</div>
                    <!-- <div class="contents-status-2"></div> -->
                </div>

                <div class="divider"></div>

                <div class="contents-details">
                    <div class="contents-details-box">
                        <div class="contents-detail-header-text">진행</div>
                        <button class="contents-details-text nickname-btn"
                                @click.prevent="nicknameClickHandler($event, data.community.memberId)">{{ data.community.nickname }}
                        </button>
                    </div>
                    <div class="contents-details-box">
                        <div class="contents-detail-header-text">장소</div>
                        <span v-if="data.community.onlineType === true" class="contents-status-2">ON-LINE</span>
                        <span v-if="data.community.onlineType === false" class="contents-status-2">OFF-LINE</span>
                        <div class="contents-details-text location-info">{{ data.community.locationInfo }}</div>
                    </div>
                    <div class="contents-details-box">
                        <div class="contents-detail-header-text">기간</div>
                        <div class="contents-details-text">{{ data.community.period }}</div>
                    </div>
                    <div class="contents-details-box">
                        <div class="contents-detail-header-text">인원</div>
                        <div class="contents-details-text">{{ data.community.teamSize }}명</div>
                        <!-- <div class="contents-details-text">(확정인원: N명)</div> -->
                    </div>
                </div>

                <div class="divider"></div>

                <p class="margin-top-3">
                    자세한 팀원 모집은 알림과 채팅 기능을 이용해보세요!
                </p>
                <div class="contents-apply-form">
                    <button v-if="data.community.closeFlag==1" @click.prevent="closeHandler" class="disabled" disabled="true">
                        모집 마감됨
                    </button>
                    <!-- 등록자인지 확인-->
                    <button v-if="teamInfo.registerFlag==1 && data.community.closeFlag !=1" @click.prevent="closeHandler">
                        모집 마감
                    </button>
                    <!--작성자가 아니고, 참여 수락했을 경우 -->
                    <button v-if="teamInfo.registerFlag!=1 && teamInfo.participateFlag==0" class="disabled" disabled="true">
                        참여중
                    </button>
                    <!-- 작성자가 아니고, 참여 거절했을 경우 1이면 게시글 작성자가 거절 -->
                    <button v-else-if="teamInfo.registerFlag!=1 && teamInfo.participateFlag==1" class="disabled" disabled="true">
                        거절됨
                    </button>
                    <!-- 마감이 안 됐고, 참여한적이 없을 때 -->
                    <button v-else-if="teamInfo.registerFlag!=1 && data.community.closeFlag!=1" @click.prevent="applyBtnClickHandler">
                        {{ btnText }}
                    </button>
                </div>
            </div>
        </div>

    </main>
    <Modal :show="showModal" @ok="dlgOkHandler" type=0 title="로그인이 필요한 기능입니다."/>
</template>

<style scoped>
main {
    margin: 30px 0;
}

.thumbnail-box {
    display: flex;
    width: 748px;
    margin: 0 auto;

    border-style: solid;
    border-width: 1px;
    border-color: #e5e5e5;
    border-radius: 8px 8px 0px 0px;

}

.disabled{
    background-color: gray !important;
}
.css-l68de9 {
    display: block;
    object-fit: cover;
    border-radius: 1px;
    width: 100%;
    height: 500px;
    pointer-events: auto;
    border-radius: 0px;

    margin: 0 auto;
}

.css-3y3f20 {
    width: 750px;
    margin: 0 auto;


}

.contents-form-relative {
    width: 708px;
    height: 420px;
    padding: 20px;
    line-height: 16px;
    border-style: none solid solid;
    border-width: 1px;
    border-color: #e5e5e5;
    border-radius: 0px 0px 8px 8px;
    background-color: #fff;
}


.contents-title-text {
    font-size: 20px;
    font-weight: 700;
    line-height: 25px;
    color: #222;
}

.contents-status-box {
    display: flex;
    flex-direction: row;
    margin-top: 10px;
    justify-content: flex-start;
}

.contents-status-1 {
    display: flex;
    justify-content: center;
    width: 63.3px;
    height: 16px;
    margin: 0px 6px 4px 0px;
    border: 0.5px none #000;
    border-radius: 3px;
    background-color: #fdecc8;
    font-size: 12px;
    font-weight: 400;
    color: #49290e;
    background-color: #feebe1;
}

.contents-status-2 {
    display: flex;
    justify-content: center;
    width: 63.3px;
    height: 16px;
    margin: auto 6px auto 15px;
    border: 0.5px none #000;
    border-radius: 3px;
    font-size: 12px;
    font-weight: 400;
    color: white;
    background-color: #8CCCF4;

}

.contents-details-box {
    display: flex;
}

.contents-details {
    display: block;
    margin-top: 16px;
}

.contents-detail-header-text {
    padding-top: 8px;
    padding-bottom: 8px;
    font-weight: 700;
}

.contents-details-text {
    margin-left: 15px;
    padding-top: 8px;
    padding-bottom: 8px;
    padding-left: 0px;
    color: #222;
}

.nickname-btn {
    cursor: pointer;
    text-decoration: underline;
    font-weight: 500;
}

.location-info {
    margin-left: 0;
}

.divider {
    width: 100%;
    margin-top: 15px;
    border-bottom: 1.5px solid #e5e5e5;
}

.contents-apply-form {
    width: 100px;
    display: flex;
    flex-direction: column;
    margin-top: 5px;
}

.contents-apply-form button {
    background-color: black;
    color: white;
    margin-top: 30px;
    padding: 7px 15px;
    border: none;
    border-radius: 4px;
    cursor: pointer;
}
</style>