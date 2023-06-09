<template>
  <form ref="uploadForm" @submit.prevent="submit">

    <div class="form-wrapper-1">
      <div class="form-wrapper-2">
        <label for="hostel-select">Выберите общежитие</label>
      </div>
      <div class="form-wrapper-2">
        <select id="hostel-select" class="form-select" v-model="selectedHostelName">
          <option v-for="hostel in hostels" v-bind:key="hostel.id">
            {{ hostel.name }}
          </option>
        </select>
      </div>
      <br>
      <div class="form-wrapper-2">
        <label for="file-upload" class="input-file">
          Выберите файл
        </label>
        <input type="file" ref="uploadExcel" @change="onExcelUpload()" class="form-control"/>
      </div>
      <div class="form-wrapper-2">
        <button class="btn btn-primary" type="button" @click="startUpload()">Загрузить файл</button>
      </div>
    </div>
  </form>
</template>
<style>
.form-select {
  border: 2px solid #646464 !important;
  border-radius: 0px !important;
  height: 60px;
  font-size: 20px;
  width: 300px !important;
}

</style>

<script>
import axios from "axios";

export default {
  name: 'load-excel',
  data: () => ({
    selectedHostelName: '',
    selectedHostelId: -1,
    formData: null,
    hostels: []
  }),
  mounted() {
    this.getAllHostels()
  },
  watch: {
    selectedHostelName(newVal) {
      // Find the hoster with the selected name
      const selectedHostel = this.hostels.find(h => h.name === newVal)
      console.log(selectedHostel)
      // Get the id of the selected hostel, or -1 if not found
      this.selectedHostelId = selectedHostel ? selectedHostel.id : -1
    },
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
        url: 'http://localhost:8081/api/v1/hostelManager/import?hostelId=' + this.selectedHostelId,
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
    },
    getAllHostels() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/hostel/all',
        method: 'GET',
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        }
      })
          .then(response => {
            this.hostels = response.data;
          })
          .catch(error => {
            console.log(error);
          });
    }
  }
}
</script>