<template>
  <nav-bar></nav-bar>
  <div class="row">
    <h3 style="margin-top: 16px;">Загрузить документы</h3>
    <form ref="uploadSpravkaForm" @submit.prevent="submit">
      <div class="form-wrapper-1">
        <div class="form-wrapper-2">
          <h6 style="margin-top: 16px;">Загрузить справки ф.086/у</h6>
          <label for="file-upload-1" class="input-file">
            Выберите файл
          </label>
          <input id="file-upload-1" type="file" ref="uploadSpravka" @change="onSpravkaUpload()" class="form-control"/>
          <button class="btn btn-primary" type="button" @click="startSpravkaUpload()">Загрузить файл</button>
        </div>
      </div>
    </form>
    <form ref="uploadPrivivkiForm" @submit.prevent="submit">
      <div class="form-wrapper-1">
        <div class="form-wrapper-2">
          <h6 style="margin-top: 16px;">Загрузить прививочный сертификат</h6>
          <label for="file-upload-2" class="input-file">
            Выберите файл
          </label>
          <input id="file-upload-2" type="file" ref="uploadPrivivki" @change="onPrivivkiUpload()" class="form-control"/>
          <button class="btn btn-primary" type="button" @click="startPrivivkiUpload()">Загрузить файл</button>
        </div>
      </div>
    </form>
    <form ref="uploadMedPolisForm" @submit.prevent="submit">
      <div class="form-wrapper-1">
        <div class="form-wrapper-2">
          <h6 style="margin-top: 16px;">Загрузить полис обязательного медицинского страхования</h6>
          <label for="file-upload-3" class="input-file">
            Выберите файл
          </label>
          <input id="file-upload-3" type="file" ref="uploadMedPolis" @change="onMedPolisUpload()" class="form-control"/>
          <button class="btn btn-primary" type="button" @click="startMedPolisUpload()">Загрузить файл</button>
        </div>
      </div>
    </form>
  </div>
</template>

<style>

h1, h2, h3, h4, h5, h6 {
  font-family: 'Manrope' !important;
}

table {
  font-family: 'Manrope' !important;
}
</style>

<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'my-documents',
  components: {
    'nav-bar': Header
  },
  data: () => ({
    spravka: null,
    privivki: null,
    medPolis: null
  }),
  methods: {
    onSpravkaUpload() {
      let file = this.$refs.uploadSpravka.files[0];
      this.spravka = new FormData();
      this.spravka.append('file', file);
    },
    onPrivivkiUpload() {
      let file = this.$refs.uploadPrivivki.files[0];
      this.privivki = new FormData();
      this.privivki.append('file', file);
    },
    onMedPolisUpload() {
      let file = this.$refs.uploadMedPolis.files[0];
      this.medPolis = new FormData();
      this.medPolis.append('file', file);
    },
    startSpravkaUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/documents/spravka',
        method: 'POST',
        data: this.spravka,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'multipart/form-data'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.$refs.uploadSpravkaForm.reset();
        window.location.reload();
      })
    },
    startPrivivkiUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/documents/privivki',
        method: 'POST',
        data: this.privivki,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'multipart/form-data'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.$refs.uploadPrivivkiForm.reset();
        window.location.reload();
      })
    },
    startMedPolisUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/documents/medPolis',
        method: 'POST',
        data: this.medPolis,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'multipart/form-data'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.$refs.uploadMedPolisForm.reset();
        window.location.reload();
      })
    }
  }
}
</script>