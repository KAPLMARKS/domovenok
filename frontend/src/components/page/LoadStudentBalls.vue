<template>
  <nav-bar></nav-bar>
  <h1 style="text-align:center; margin-top: 16px;">Загрузка баллов студентов</h1>
  <form style="margin-left: 16px" ref="uploadForm" @submit.prevent="submit">
    <div class="form-wrapper-1">
      <div class="form-wrapper-2">
        <label for="file-upload" class="input-file">
          Выберите файл
        </label>
        <input id="file-upload" type="file" ref="uploadExcel" @change="onExcelUpload()" class="form-control"/>
      </div>
      <div class="form-wrapper-2">
        <button class="btn btn-primary" type="button" @click="startUpload()">Загрузить файл</button>
      </div>
    </div>
  </form>
</template>
<script>
import axios from "axios";
import Header from "@/components/components/Header";

export default {
  name: 'load-student-balls-page',
  components: {
    "nav-bar": Header,
  },

  data() {
    return {
      formData: null,
    }
  },

  methods: {
    onExcelUpload() {
      let file = this.$refs.uploadExcel.files[0];
      this.formData = new FormData();
      this.formData.append('file', file);
    },
    startUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/student/import/balls',
        method: 'POST',
        data: this.formData,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'multipart/form-data'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.$refs.uploadForm.reset();
        window.location.reload();
      })
    }
  }
}
</script>