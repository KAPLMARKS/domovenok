<template>
    <div class="row">
      <table class="table table-striped">
        <thead>
        <tr>
          <th scope="col">#</th>
          <th scope="col">Почта</th>
          <th scope="col">Фамилия</th>
          <th scope="col">Имя</th>
          <th scope="col">Отчество</th>
          <th scope="col">Должность</th>
        </tr>
        </thead>
        <tbody>
        <tr v-for="employee in employees" :key="employee.id">
          <td>{{employee.id}}</td>
          <td>{{employee.email}}</td>
          <td>{{employee.lastName}}</td>
          <td>{{employee.firstName}}</td>
          <td>{{employee.middleName}}</td>
          <td>{{employee.dolgnost}}</td>
          <button class="btn" @click="deleteUser(employee.id)">Изменить сотрудника</button>
          <button class="btn" @click="deleteUser(employee.id)">Удалить сотрудника</button>
        </tr>
        </tbody>
      </table>
    </div>
</template>
<style>

</style>
<script>
import axios from "axios";

export default {
  name: 'list-employees',
  data() {
    return {
      employees: [],
    };
  },
  mounted() {
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/employee', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
          // handle success response
          this.employees = response.data;
        })
        .catch(error => {
          // handle error response
          console.error(error);
        });
  },
  methods: {
    deleteUser(employeeId) {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.delete(`http://localhost:8081/api/v1/employee/delete/${employeeId}`,{
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      })
          .then(response => {
            console.log(response.data);
            window.location.reload();
          })
          .catch(error => {
            console.log(error.response.data);
          });
    }
  }
}
</script>