<script setup>
import { useUserDetailsStore } from "@/stores/useUserDetailsStore";
import { reactive, ref } from "vue";

// Dummy data
const portfolioCopyright = [
  { name: "cc" },
  { name: "by" },
  { name: "nc" },
  { name: "nd" },
];

const portfolios = [
  { id: 1, title: "목록1", thumbnail: "aurora-over-iceland.png" },
  { id: 2, title: "목록2", thumbnail: "calm.jpg" },
  { id: 3, title: "목록3", thumbnail: "cherryblossom.jpg" },
  { id: 4, title: "목록4", thumbnail: "corn.jpg" },
];

const props = defineProps({
  portfolio: {
    type: Object,
    default: () => ({
      awardDate: null,
    }),
  },
  title: {
    type: String,
    required: true,
  },
  member: {
    type: Object,
    default: () => ({
      nickname: useUserDetailsStore().nickname,
      image: useUserDetailsStore().profileSrc,
    }),
  },
  contents: {
    type: Array,
    default: () => [],
  },
  skills: {
    type: Array,
    default: () => [],
  },
});

const state = reactive({
  visible: false,
});

const open = () => {
  state.visible = true;
};

const close = () => {
  state.visible = false;
}

defineEmits(["close"])

defineExpose({
  open,
  close,
});
</script>

<template>
  <div
    class="absolute min-h-full max-h-fit w-full gap-y-2 bg-gray-50 xl:grid xl:grid-cols-12 xl:px-16 xl:pb-8"
    v-if="state.visible"
  >
    <div
      class="mt-16 w-full bg-white pb-4 lg:col-span-9 lg:ml-36 lg:rounded-lg lg:border"
    >
      <!-- Profile -->
      <figure class="flex border-b p-6">
        <img
          class="mr-4 mt-2 h-12 w-12 cursor-pointer rounded-full duration-300 hover:opacity-50"
          :src="`http://localhost:8080/profileImage/${props.member.image}`"
          alt="Profile image"
        >

        <figcaption class="flex w-full cursor-default flex-col">
          <h1
            class="text-sm font-bold sm:text-2xl"
            v-text="props.title"
          />
          <div class="flex">
            <div>
              <span
                class="w-fit cursor-pointer text-xs font-semibold hover:text-gray-500 sm:text-lg"
                v-text="props.member.nickname"
              />
            </div>
            <span class="text-xs sm:text-lg">ᆞ</span>
            <span
              class="w-fit cursor-pointer text-xs font-semibold hover:text-gray-500 sm:text-lg"
            >팔로우</span>
          </div>
        </figcaption>
        <div
          class="relative x-mark-icon cursor-pointer bg-cover bg-no-repeat opacity-50 hover:opacity-100"
          @click="$emit('close')"
        />
      </figure>

      <!-- Main -->
      <main>
        <template
          :key="content.index"
          v-for="content in props.contents"
        >
          <img
            :key="src.index"
            v-for="src in content.img"
            :src="src"
            alt="Content image"
          >
          <p
            v-if="content.type === 'text'"
            v-html="content.text"
          />
        </template>
      </main>

      <!-- SkillTags and Copyright bar -->
      <div
        class="flex flex-col-reverse justify-between px-5 sm:flex-row sm:px-10 sm:pb-12 xl:py-10"
      >
        <!-- SkillTags -->
        <nav class="flex gap-x-2 py-3 text-xs sm:px-0">
          <div
            class="block w-fit cursor-pointer rounded-full border px-3 py-1 font-semibold text-gray-600 hover:bg-blue-50"
            v-for="skill in props.skills"
            :key="skill.engName"
            v-text="skill.engName"
          />
        </nav>
        <!-- Copyright -->
        <div class="w- flex items-center gap-x-1 hover:cursor-pointer">
          <img
            :src="`/src/assets/images/${copyright.name}.svg`"
            alt="copyright"
            class="h-6 w-6"
            :key="copyright.name"
            v-for="copyright in portfolioCopyright"
          >
        </div>
      </div>
      <!-- Banner -->
      <div
        class="flex h-48 flex-col items-center justify-between bg-gray-950 py-9 sm:h-56 sm:py-10"
      >
        <div class="flex w-32 justify-evenly">
          <div
            class="mb-2 flex h-12 w-12 cursor-pointer flex-col items-center justify-center rounded-full bg-white"
          >
            <div
              class="absolute flex h-12 w-12 items-center justify-center hover:animate-pulse"
            >
              <div class="h-6 w-6 bg-heart bg-center bg-no-repeat" />
            </div>
            <span class="bottom-0.5 mt-4 hidden text-xs font-bold text-white" />
          </div>
          <div
            class="collection-icon mb-2 cursor-pointer rounded-full border-2 bg-white duration-300 hover:bg-blue-50"
          />
        </div>
        <span
          class="text-sm font-bold text-blue-300"
          v-if="props.portfolio.awardDate != null"
        >POFO PICK 선정</span>
        <span
          class="text-lg font-bold text-white sm:text-xl"
          v-text="props.title"
        />
        <div>
          <span
            class="text-xs font-semibold text-white sm:text-sm"
            v-if="props.portfolio.awardDate != null"
            v-text="
              `${props.portfolio.awardDate
                .substring(0, 10)
                .replace(/-/g, '.')}` + ' | '
            "
          />
          <span
            class="text-xs font-semibold text-white sm:text-sm"
            v-text="
              props.portfolio.collaboration === 0
                ? '개인 프로젝트'
                : '팀 프로젝트'
            "
          />
        </div>
      </div>

      <!-- Member's portfolio list bar -->
      <div class="flex justify-between px-7 py-6">
        <span class="block font-semibold text-gray-900">모든 작업 목록</span>
        <div class="flex items-center justify-end">
          <span class="block text-sm font-semibold text-gray-500">프로필 자세히 보기</span>
          <img
            src="/src/assets/images/chevron-right.svg"
            alt="Chevron right icon"
            class="mt-0.5 h-4 w-4 opacity-50"
          >
        </div>
      </div>

      <!-- Member's portfolio list -->
      <div class="w-full">
        <div
          class="scroll-container scrollbar-hide mx-8 flex h-60 overflow-x-scroll scroll-smooth md:gap-x-1.5 xl:gap-x-9"
        >
          <figure
            class="h-48 w-96 cursor-pointer"
            :key="morePortfolio.id"
            v-for="morePortfolio in portfolios"
          >
            <div>
              <img
                :src="`http://localhost:8080/portfolio/thumbnails/${morePortfolio.thumbnail}`"
                alt="#"
                class="h-48 w-72 rounded-t-lg"
              >
              <figcaption
                class="w-72 rounded-b-lg bg-gray-950 px-5 text-sm font-bold text-white"
                v-text="morePortfolio.title"
              />
            </div>
          </figure>
        </div>

        <!-- Scroll buttons -->
        <div class="relative bottom-40 flex h-0 w-full justify-between px-4">
          <div
            class="chevron-left-icon cursor-pointer border bg-white shadow-lg hover:bg-blue-50 hover:duration-300"
          />
          <div
            class="chevron-right-icon col-start-12 mr-0 cursor-pointer justify-self-center border bg-white shadow-lg hover:bg-blue-50 hover:duration-300"
          />
        </div>
      </div>
    </div>
  </div>
</template>

<style scoped>
@import url("/src/assets/css/tailwind.css");

main:deep(img) {
  @apply mb-12 h-1/5 w-full;
}

main:deep(p) {
  @apply mx-6 mb-12 text-base font-bold sm:text-2xl xl:mx-12;
}

main:deep(div) {
  @apply text-sm font-normal sm:text-base;
}

.chevron-left-icon {
  width: 48px;
  height: 48px;
  background-image: url("/src/assets/images/chevron-left.svg");
  background-position: center;
  background-size: 50%;
  background-repeat: no-repeat;
  border-radius: 100%;
}

.chevron-right-icon {
  width: 48px;
  height: 48px;
  background-image: url("/src/assets/images/chevron-right.svg");
  background-position: center;
  background-size: 50%;
  background-repeat: no-repeat;
  border-radius: 100%;
  right: 23.7rem;
}

.collection-icon {
  width: 48px;
  height: 48px;
  background-image: url("/src/assets/images/folder.svg");
  background-position: center;
  background-size: 50%;
  background-repeat: no-repeat;
  border-radius: 100%;
}

.x-mark-icon {
  width: 28px;
  height: 28px;
  background-image: url("/src/assets/images/x-mark.svg");
}

/* For Webkit-based browsers (Chrome, Safari and Opera) */
.scrollbar-hide::-webkit-scrollbar {
  display: none;
}

/* For IE, Edge and Firefox */
.scrollbar-hide {
  -ms-overflow-style: none; /* IE and Edge */
  scrollbar-width: none; /* Firefox */
}
</style>
