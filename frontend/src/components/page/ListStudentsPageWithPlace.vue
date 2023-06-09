<template>
  <nav-bar></nav-bar>
  <h1 style="margin-top: 16px; text-align: center">Список заселенных студентов</h1>

  <div class="row">
    <input type="text" v-model="search" class="form-control" placeholder="Поиск" style="max-width: 99%;">
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Почта</th>
        <th scope="col">Фамилия</th>
        <th scope="col">Имя</th>
        <th scope="col">Отчество</th>
        <th scope="col">Пол</th>
        <th scope="col">Категория</th>
        <th scope="col">Форма обучения</th>
        <th scope="col">Уровень образования</th>
        <th scope="col">Курс</th>
        <th scope="col">Факультет</th>
        <th scope="col">Возраст</th>
        <th scope="col">Общежитие</th>
        <th scope="col">Комната</th>
        <th scope="col">Место</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="student in filteredStudents" :key="student.id">
        <td>{{student.id}}</td>
        <td>{{student.email}}</td>
        <td>{{student.lastName}}</td>
        <td>{{student.firstName}}</td>
        <td>{{student.middleName}}</td>
        <td>{{student.male ? 'Мужской' : 'Женский'}}</td>
        <td v-if="student.studentCategory ==='BUDGET'">Бютжетник</td>
        <td v-if="student.studentCategory ==='PAID'">Платник</td>
        <td v-if="student.studentCategory ==='CONTRACT'">Контрактник</td>
        <td v-if="student.formEducation === 'FULL_TIME'">Очная</td>
        <td v-else-if="student.formEducation === 'EXTRAMURAL'">Заочная</td>
        <td v-else-if="student.formEducation === 'PART_TIME'">Очно-заочная</td>
        <td v-if="student.qualificationType === 'BAKALAVR'">Бакалавриат</td>
        <td v-else-if="student.qualificationType === 'MAGISTR'">Магистратура</td>
        <td>{{student.course}}</td>
        <td>{{student.faculty}}</td>
        <td>{{student.age}}</td>
        <td>{{student.place.room.hostel.name}}</td>
        <td>{{student.place.room.name}}</td>
        <td>{{student.place.number}}</td>
        <button class="btn" @click="removePlace(student.id)">Выселить студента</button>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<style>

</style>
<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'list-students-with-places',
  components: {
    'nav-bar': Header
  },
  data(){
    return {
      search: '',
      students: [],
    }
  },
  mounted() {
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/student/with-places', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      console.log(response.data)
      this.students = response.data;
    })
        .catch(error => {
          // handle error response
          console.error(error);
        });
  },
  computed: {
    filteredStudents: function() {
      const keyword = this.search.toLowerCase()
      return this.students.filter(function(student) {
        const fullName = `${student.lastName} ${student.firstName} ${student.middleName}`.toLowerCase()
        return (
            student.email.toLowerCase().indexOf(keyword) > -1 ||
            fullName.indexOf(keyword) > -1 ||
            student.citizenship.toLowerCase().indexOf(keyword) > -1 ||
            student.faculty.toLowerCase().indexOf(keyword) > -1
        )
      })
    }
  },
  methods: {
    removePlace(studentId) {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.post(`http://localhost:8081/api/v1/student/remove/${studentId}`,{
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