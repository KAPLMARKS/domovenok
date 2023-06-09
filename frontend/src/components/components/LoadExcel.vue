<template>
  <form ref="uploadForm" @submit.prevent="submit">
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

<style>
.form-wrapper-1 {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}

.input-file {
  border: 2px solid #646464;
  display: inline-block;
  width: 300px;
  height: 60px;
  padding: 6px 12px;
  cursor: pointer;
  font-size: var(--bs-btn-font-size);
  font-weight: bold !important;
  text-align: center;
  color: #646464;
}

.input-file:hover {
  background-color: #646464;
  border: 2px solid #646464;
  color: white;

}

input[type="file"] {
  display: none;
}

</style>

<script>
import axios from "axios";

export default {
  name: 'load-excel',
  data: () => ({
    formData: null,
  }),
  methods: {
    onExcelUpload() {
      let file = this.$refs.uploadExcel.files[0];
      this.formData = new FormData();
      this.formData.append('file', file);
    },
    startUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/employee/import',
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