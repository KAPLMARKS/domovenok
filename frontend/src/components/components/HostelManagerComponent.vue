<template>
  <form ref="anyName" @submit.prevent="submitForm">
    <div class="form-wrapper-1">
      <label for="hostel-select">Выберите общежитие</label>
      <select id="hostel-select" class="form-select" v-model="selectedHostelName">
        <option v-for="hostel in hostels" v-bind:key="hostel.id">
          {{ hostel.name }}
        </option>
      </select>
      <label>Почта</label>
      <input class="form-control" type="email" v-model="email" required>

      <label>Имя</label>
      <input class="form-control" type="text" v-model="firstName" required>

      <label>Отчество</label>
      <input class="form-control" type="text" v-model="middleName">

      <label>Фамилия</label>
      <input class="form-control" type="text" v-model="lastName" required>

      <label>Пароль</label>
      <input class="form-control" type="password" v-model="password" required>

      <button class="btn btn-primary" type="submit" style="margin-left: 16px">Добавить</button>
    </div>
  </form>
</template>
<script>
import axios from "axios";

export default {
  name: 'hostel-manager-component',
  data() {
    return {
      selectedHostelName: '',
      selectedHostelId: -1,
      email: '',
      firstName: '',
      middleName: '',
      lastName: '',
      dolgnost: '',
      password: '',
      roles: '',
      hostels: []
    }
  },
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
    submitForm() {
      const formData = {
        email: this.email,
        firstName: this.firstName,
        middleName: this.middleName,
        lastName: this.lastName,
        dolgnost: 'Заведующий общежитием',
        password: this.password,
        roles: 'HOSTEL_MANAGER'
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/hostelManager/?hostelId=' + this.selectedHostelId,
        method: 'POST',
        data: formData,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data));
        window.location.reload();
        this.$refs.anyName.reset();
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