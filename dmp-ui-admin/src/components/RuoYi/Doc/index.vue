<template>
  <div>
    <svg-icon icon-class="question" @click="goto"/>
  </div>
</template>

<script>
export default {
  name: 'DmpDoc',
  data() {
    return {
      url: 'http://www.iocoder.cn/Dmp/build-debugger-environment/?dmp'
    }
  },
  methods: {
    goto() {
      window.open(this.url)
    }
  }
}
</script>
