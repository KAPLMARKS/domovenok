<template>
  <nav-bar></nav-bar>
  <h1 style="text-align:center; margin-top: 16px;">Статистика</h1>
  <div class="row">
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Название общежития</th>
        <th scope="col">Места</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="statistic in statistics" :key="statistic.id">
        <td>{{statistic.id}}</td>
        <td>{{statistic.hostel.name}}</td>
        <td>{{ statistic.freePlaces }}/{{ statistic.allPlaces }}</td>
      </tr>
      </tbody>
    </table>
  </div>
  <button @click="handlePrediction" style="margin-left: 16px !important;">Анализ на 2023 год</button>
  <p>{{prediction}}</p>
</template>
<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'statistics-page',
  components: {
    'nav-bar': Header
  },
  data() {
    return {
      statistics: [],
      prediction: ""
    };
  },
  mounted() {
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/statistics', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      // handle success response
      this.statistics = response.data;
    })
        .catch(error => {
          // handle error response
          console.error(error);
        });
  },
  methods: {
    handlePrediction() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get('http://localhost:8081/api/v1/prediction',
          {
            headers: {
              Accept: '*/*',
              Authorization: `Bearer ${user.token}`,
            }
          }
      )
          .then(response => {
           this.prediction =  response.data
          })
          .catch(error => {
            console.error(error)
          });
    }
  }

}
</script>