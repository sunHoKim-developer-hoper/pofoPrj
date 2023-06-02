<script setup>
import Header from '../Header.vue'
</script>
<template>
    <Header></Header>
    <div class="container">
        <div class="border-blue"></div>
        <section class="team-c-title margin-top-2">
            <h1 class="team-title margin-top-7">팀 개설하기 안내</h1>
            <img class="team-img" src="/src/assets/images/diversity.png" alt="">
            <p class="text margin-top-5">새로운 주제로 팀 프로젝트를 진행하고 싶으신가요 ? <br>
                <br>
                <br> 
                <br> 
                
                <strong>프로젝트 목표!</strong>
                <br>
                이 목표는 당신의 팀이 어떤 프로젝트를 하고 싶은지,<br> 
                  그리고 그 목표를 달성하기 위해 필요한 사람들을 모으는 것입니다.<br> 
                목표를 설정하면 팀을 개설하고 활동을 계획하는 데 도움이 됩니다.<br>
            아래 양식에 맞게 입력을 해주시면 새로운 팀 프로젝트가 진행됩니다!<br>
            자세한 팀원 모집은 알림과 채팅 기능을 이용해보세요 !
            <img class="noti" src="/src/assets/images/notification.png" alt="">
            </p>
        </section>
        <form class="team-c-form" action="">
            <main class="team-c-main margin-top-1">
            <div class="first-img-box">
                <div class="img-box">
                    <input type="file" class="d-none">
                    <img class="hover" src="/src/assets/images/img.png" alt="">
                    <span>썸네일 대표 이미지를 추가해주세요.</span>
                    <img class="hover d-none" src="/src/assets/images/fff-img.png" alt="">
                </div>
            </div>
            <div class="text-box margin-top-3">
                <h2>제목</h2>
                <input class="team-c-input" type="text" placeholder="내 답변">
            </div>
            <div class="text-box margin-top-3">
                <h2>닉네임</h2>
                <!-- 고정값 -->
                <input class="team-c-input" type="text" placeholder="닉네임">
            </div>
            <div class="button-box margin-top-3">
                <h2>온오프상태</h2>
                <div  class="btn-box margin-top-5">
                <button class="btn btn-1 margin-right-10">onLine</button>
                <button class="btn btn-1">offLine</button>
            </div>
            </div>
            <div class="text-box margin-top-3">
                <h2>장소</h2>
                <input class="team-c-input" type="text" placeholder="내 답변">
            </div>
            <div class="text-box margin-top-3">
                <h2>기간</h2>
                <input class="team-c-input" type="text" placeholder="내 답변">
            </div>
            <div class="text-box margin-top-3">
                <h2>모집인원</h2>
                <input class="team-c-input" type="text" placeholder="내 답변">
            </div>
            <div class="submit-box margin-top-2">
                <button class="submit" type="submit">등록</button>
                <button class="reset">양식지우기</button>
            </div>
        </main>
        </form>
    </div>
</template>
<style scoped>
@import url("/src/assets/css/compoment/team-register.css");
</style>