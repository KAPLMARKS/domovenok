<template>
  <nav-bar></nav-bar>
  <h3 style="margin-top: 16px; margin-left: 16px">Список заявок</h3>
  <div class="form-items" style="margin-left: 16px">
    <select v-model="type" @change="getClaims" class="form-control" style="max-width: 99%;">
      <option value="WISH">Заявки на заселение</option>
      <option value="EVICTION">Заявки на выселение</option>
      <option value="RELOCATION">Заявки на переселение</option>
      <option value="PROBLEM">Бытовая заявки</option>
    </select>
  </div>
  <table class="table table-striped">
    <thead>
    <tr>
      <th scope="col">Общежитие</th>
      <th scope="col">Комната</th>
      <th scope="col">Место</th>
      <th scope="col">Тип</th>
      <th scope="col">Статус</th>
      <th scope="col">Рейтинг студента</th>
      <th scope="col">Сообщение</th>
    </tr>
    </thead>
    <tbody>
    <tr v-for="claim in claims" :key="claim.id">
      <td>{{claim.place.room.hostel.name}}</td>
      <td>{{claim.place.room.name}}</td>
      <td>{{ claim.place.number }}</td>
      <td v-if="claim.type === 'WISH'">Заявка на заселение</td>
      <td v-else-if="claim.type === 'EVICTION'">Заявка на выселение</td>
      <td v-else-if="claim.type === 'RELOCATION'">Заявка на переселение</td>
      <td v-else>Бытовая заявка</td>
      <td v-if="claim.answerType === 'WAIT'">Ожидайте</td>
      <td v-else-if="claim.answerType === 'SUCCESS'">Принято</td>
      <td v-else>Отклонено</td>
      <td>{{claim.declarer.rating}}</td>
      <td>{{claim.message}}</td>
      <button class="btn" @click="successClaim(claim.id)">Одобрить</button>
      <button class="btn" @click="declineClaim(claim.id)">Отклонить</button>
    </tr>
    </tbody>
  </table>
</template>
<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'hostel-claims',
  components: {
    "nav-bar": Header,
  },
  data() {
    return {
      type: 'WISH',
      claims: [],
    }
  },
  mounted() {
    this.getClaims()
  },
  methods: {
    getClaims() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get(`http://localhost:8081/api/v1/hostelManager/claims/type?type=` + this.type, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        },
      })
          .then(response => {
            console.log(response.data);
            this.claims = response.data
          })
          .catch(error => {
            console.log(error.response);
          });
    },
    successClaim(claimId) {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.put(`http://localhost:8081/api/v1/claims/editStatus?claimId=` + claimId,{}, {
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
  }
}
</script>