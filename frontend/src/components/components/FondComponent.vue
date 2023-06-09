<template>
  <nav-bar></nav-bar>
  <h1 style="margin-top: 16px; text-align: center">Жилой фонд</h1>
  <ul class="nav justify-content-center">
    <li class="nav-item">
      <h5><router-link class="nav-link active" to="/fond">Жилой фонд</router-link></h5>
    </li>
    <li class="nav-item">
      <h5><router-link class="nav-link" to="/fond">Добавление зданий</router-link></h5>
    </li>
    <li class="nav-item">
      <h5><router-link class="nav-link" to="/fond">Добавление комнат</router-link></h5>
    </li>
    <li class="nav-item">
      <h5><router-link class="nav-link" to="/fond">Добавление мест</router-link></h5>
    </li>
  </ul>
  <div class="row">
    <form>
      <button type="button" class="btn btn-outline-primary">Добавить здание</button>
    </form>
  </div>
</template>
<style>
.active {
  color: darkgreen;
  border-radius: 20px; /* adjust the value to change the oval shape */
  border: 2px solid green;
  padding: 10px 20px; /* adjust the values to change the button size */
  font-size: 16px;
  font-weight: bold;
  background-color: white;
  cursor: pointer;
}
</style>
<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'fond-component',
  components: {
    "nav-bar": Header
  },
  data() {
    return {
      name: '',
      countFloors: '',
      countRooms: '',
      countPlaces: ''
    }
  },
  methods: {
    createHostel() {
      const hostel = {
        name: this.name,
        countFloors: this.countFloors,
        countRooms: this.countRooms,
        countPlaces: this.countPlaces,
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/hostel',
        method: 'POST',
        data: hostel,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      })
          .then(response => {
            console.log(response.data);
            window.location.reload();
            this.$refs.anyName.reset();
          })
          .catch(error => {
            console.log(error.response.data);
          });
    }
  }
}
</script>