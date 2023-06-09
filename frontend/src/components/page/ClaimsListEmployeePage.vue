<template>
  <nav-bar></nav-bar>
  <h1 style="text-align: center; margin-top: 16px;">Заявки студентов</h1>
  <div class="row">
    <select @change="onChange" v-model="answerType" class="form-control" style="max-width: 99%;">
      <option disabled value="">Выберите заявки</option>
      <option value="WAIT">Новые заявки</option>
      <option value="SUCCESS">Одобренные заявки</option>
      <option value="DECLINED">Отклоненные заявки</option>
    </select>
    <table v-if="answerType === 'WAIT'" class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Общежитие</th>
        <th scope="col">Комната</th>
        <th scope="col">Место</th>
        <th scope="col">Тип</th>
        <th scope="col">Статус</th>
        <th scope="col">ФИО студента</th>
        <th scope="col">Рейтинг студента</th>
        <th scope="col">Сообщение</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="claim in claims" :key="claim.id">
        <td>{{claim.place.room.hostel.name}}</td>
        <td>{{claim.place.room.name}}</td>
        <td>{{ claim.place.number }}</td>
        <td>{{ claim.type }}</td>
        <td>{{ claim.answerType }}</td>
        <td>{{ claim.declarer.lastName + ' ' + claim.declarer.firstName + ' ' + claim.declarer.middleName }}</td>
        <td>{{claim.message}}</td>
        <button class="btn" @click="successClaim(claim.id)">Одобрить</button>
        <button class="btn" @click="declineClaim(claim.id)">Отклонить</button>
      </tr>
      </tbody>
    </table>
    <table v-if="answerType === 'SUCCESS'" class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Общежитие</th>
        <th scope="col">Комната</th>
        <th scope="col">Место</th>
        <th scope="col">Тип</th>
        <th scope="col">Статус</th>
        <th scope="col">ФИО студента</th>
        <th scope="col">Рейтинг студента</th>
        <th scope="col">Сообщение</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="claim in claims" :key="claim.id">
        <td>{{claim.place.room.hostel.name}}</td>
        <td>{{claim.place.room.name}}</td>
        <td>{{ claim.place.number }}</td>
        <td>{{ claim.type }}</td>
        <td>{{ claim.answerType }}</td>
        <td>{{ claim.declarer.lastName + ' ' + claim.declarer.firstName + ' ' + claim.declarer.middleName }}</td>
        <td>{{claim.declarer.rating}}</td>
        <td>{{ claim.message }}</td>
      </tr>
      </tbody>
    </table>
    <table v-if="answerType === 'DECLINED'" class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Общежитие</th>
        <th scope="col">Комната</th>
        <th scope="col">Место</th>
        <th scope="col">Тип</th>
        <th scope="col">Статус</th>
        <th scope="col">ФИО студента</th>
        <th scope="col">Рейтинг студента</th>
        <th scope="col">Сообщение</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="claim in claims" :key="claim.id">
        <td>{{claim.place.room.hostel.name}}</td>
        <td>{{claim.place.room.name}}</td>
        <td>{{ claim.place.number }}</td>
        <td>{{ claim.type }}</td>
        <td>{{ claim.answerType }}</td>
        <td>{{ claim.declarer.lastName + ' ' + claim.declarer.firstName + ' ' + claim.declarer.middleName }}</td>
        <td>{{claim.declarer.rating}}</td>
        <td>{{claim.message}}</td>
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
  name: 'claims-list',
  components: {
    'nav-bar': Header
  },
  data() {
    return {
      answerType: '',
      claims: []
    }
  },
  methods: {
    successClaim(claimId) {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.put(`http://localhost:8081/api/v1/claims/success?claimId=` + claimId, {}, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        },
      })
          .then(response => {
            console.log(response.data);
            window.location.reload();
          })
          .catch(error => {
            console.log(error.response);
          });
    },
    declineClaim(claimId) {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.put(`http://localhost:8081/api/v1/claims/declined?claimId=` + claimId, {}, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        },
      })
          .then(response => {
            console.log(response.data);
            window.location.reload();
          })
          .catch(error => {
            console.log(error.response);
          });
    },
    onChange() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get('http://localhost:8081/api/v1/claims/answerType', {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        },
        params: {
          answerType: this.answerType
        }
      }).then(response => {
        console.log(response.data)
        this.claims = response.data;
      })
          .catch(error => {
            // handle error response
            console.error(error);
          });
    },
  }
}
</script>