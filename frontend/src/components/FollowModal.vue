<template>
    <div class="black-bg">
        <div class="white-bg">
            <div>
                <h3 class="flex-row-between1">{{ `${title}(${list.length})` }}
                    <img class="엑스" src="/src/assets/images/xMark.png" @click="$emit('ok')" />
                </h3>
            </div>

            <span v-for="following in list" v-if="type == 0">
                <div class="line margin-top-2 flex-row-between1"></div>

                <div class="flex-row-between1">
                    <span class="hover margin-top-2" @click="changeURL($event, following.id), $emit('ok')" >
                        <img class="profile-img margin-top-2 " v-if="!following.image" src="/src/assets/images/proflie.svg">
                        <img class="profile-img margin-top-2 " v-else
                            :src="'http://localhost:8080/profileImage/' + following.image">
                    </span>
                    <div class="flex-row-between1">
                        <h4 class="margin-left-5 margin-top-6">{{ following.nickname }}</h4>
                        <button class="follow-btn2 margin-top-4" @click.prevent="$emit('cancleFollow', following.id)"
                            v-if="!notMyProfile">팔로우 취소</button>
                    </div>
                </div>
            </span>

            <span v-for="(follower, index) in list" v-if="type == 1">
                <div class="line margin-top-2 flex-row-between1"></div>
                <div class="flex-row-between1">
                    <span class="hover margin-top-2" @click="changeURL($event, follower.id), $emit('ok')">
                        <img class="profile-img margin-top-2 " v-if="!follower.image" src="/src/assets/images/proflie.svg">
                        <img class="profile-img margin-top-2 " v-else
                            :src="'http://localhost:8080/profileImage/' + follower.image">
                    </span>
                    <div class="flex-row-between1">
                        <h4 class="margin-left-5 margin-top-6">{{ follower.nickname }}</h4>
                        <button class="follow-btn margin-top-4" v-if="!notMyProfile && currentFollowingList[index] == 0"
                            @click.prevent="$emit('follow', follower.id)">+ 팔로우</button>
                        <button class="follow-btn3 margin-top-4"
                            v-if="!notMyProfile && currentFollowingList[index] == 1">팔로잉 중</button>
                    </div>
                </div>
            </span>
            <!--여기까지-->
        </div>
    </div>
</template>

<style scoped>
@import url("/src/assets/css/compoment/followmodal.css");

.hover:hover {
    cursor: pointer;
}
</style>

<script setup>
import { useRouter, useRoute } from 'vue-router';
let router = useRouter();
let props = defineProps({
    title: "",
    show: false,
    type: 0,
    list: null,
    currentFollowingList: null,
    notMyProfile: false
});

function changeURL(e, id) {
    router.push("/profile/"+id);
}

</script>
