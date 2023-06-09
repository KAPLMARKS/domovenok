<template>
  <div class="row">
    <h1 style="margin-top: 16px">Список жилого фонда</h1>
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">#</th>
        <th scope="col">Название</th>
        <th scope="col">Кол-во этажей</th>
        <th scope="col">Кол-во комнат</th>
        <th scope="col">Кол-во мест</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="hostel in hostels" :key="hostel.id">
        <td>{{ hostel.id }}</td>
        <td>{{ hostel.name }}</td>
        <td>{{ hostel.countFloors }}</td>
        <td>{{ hostel.countRooms }}</td>
        <td>{{ hostel.countPlaces }}</td>
        <td>
          <button class="btn" @click="openTable(hostel.id)">Посмотреть комнаты</button>
        </td>
        <td>
          <table :id="'rooms_' + hostel.id" class="table table-striped" style="display: none">
            <thead>
            <tr>
              <th scope="col">#</th>
              <th scope="col">Название</th>
              <th scope="col">Кол-во мест</th>
              <th scope="col">Этаж</th>
              <th scope="col">Пол</th>
            </tr>
            </thead>
            <tbody>
            <tr v-for="room in rooms" :key="room.id">
              <td>{{ room.id }}</td>
              <td>{{ room.name }}</td>
              <td>{{ room.countPlaces }}</td>
              <td>{{ room.floor }}</td>
              <td>{{ room.male ? 'Мужской' : 'Женский' }}</td>
            </tr>
            </tbody>
          </table>
        </td>
      </tr>
      </tbody>
    </table>
  </div>
</template>
<style>
body {
  overflow-y: auto;
}
</style>
<script>
import axios from "axios";

export default {
  name: 'create-rooms-component',

  data() {
    return {
      hostels: [],
      rooms: [],
      look: 'none'
    }
  },
  mounted() {
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/hostel/all', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      // handle success response
      this.hostels = response.data;
    })
        .catch(error => {
          // handle error response
          console.error(error);
        });
  },
  methods: {
    openTable(hostelId) {
      var table = document.getElementById("rooms_" + hostelId);
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get(`http://localhost:8081/api/v1/hostel/${hostelId}/all`, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        }
      }).then(response => {
        // handle success response
        this.rooms = response.data;
        if (table.style.display === "none") {
          table.style.display = "block";
        } else {
          table.style.display = "none";
        }
      })
          .catch(error => {
            // handle error response
            console.error(error);
          });
    }
  }
}
</script>