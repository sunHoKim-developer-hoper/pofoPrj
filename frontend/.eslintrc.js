require("@rushstack/eslint-patch/modern-module-resolution")

module.exports = {
    extends: [
        "@vue/eslint-config-prettier/skip-formatting",
        // add more generic rulesets here, such as:
        // 'eslint:recommended',
        'plugin:vue/vue3-strongly-recommended'
        // 'plugin:vue/recommended' // Use this if you are using Vue.js 2.x.
    ],
    rules: {
        // override/add rules settings here, such as:
        // 'vue/no-unused-vars': 'error'
    }
}