<template>
  <nav-bar></nav-bar>
  <div class="row">
    <button @click="downloadPdf">Скачать квитанцию</button>
  </div>
</template>

<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'receipt-page',
  components: {
    'nav-bar': Header
  },
  methods: {
    downloadPdf() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios
          .get(`http://localhost:8081/api/v1/receipt/download`, {
            headers: {
              Accept: '*/*',
              Authorization: `Bearer ${user.token}`,
            },
            responseType: 'blob'
          })
          .then(function (response) {
            let blob = new Blob([response.data], {type: 'application/pdf'})
            let link = document.createElement('a')
            link.href = window.URL.createObjectURL(blob)
            link.download = 'Report.pdf'
            link.click()
          })
    }
  },
};
</script>