<template>
  <form ref="anyName" @submit.prevent="submitForm">
    <div class="form-wrapper-1">
      <label >Почта</label>
      <input class="form-control" type="email" v-model="email" required>

      <label>Имя</label>
      <input class="form-control" type="text" v-model="firstName" required>

      <label>Отчество</label>
      <input class="form-control" type="text" v-model="middleName">

      <label>Фамилия</label>
      <input class="form-control" type="text" v-model="lastName" required>

      <label>Пароль</label>
      <input class="form-control" type="password" v-model="password" required>

      <button class="btn btn-primary" type="submit">Добавить</button>
    </div>
  </form>
</template>

<style>
.form-wrapper-1 {
  width: 300px;
  font-family: 'Manrope' !important;
}

.form-control {
  font-family: 'Manrope' !important;
  font-size: 16px !important;
}

.form-wrapper-1 label {
  font-family: 'Manrope' !important;
}

</style>

<script>
import axios from "axios";

export default {
  name: 'employee-component',
  data() {
    return {
      email: '',
      firstName: '',
      middleName: '',
      lastName: '',
      dolgnost: '',
      password: '',
      roles: ''
    }
  },
  methods: {
    submitForm() {
      const formData = {
        email: this.email,
        firstName: this.firstName,
        middleName: this.middleName,
        lastName: this.lastName,
        dolgnost: 'Студ городок',
        password: this.password,
        roles: 'STUD_GORODOK'
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/employee/',
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
    }
  }
}
</script>