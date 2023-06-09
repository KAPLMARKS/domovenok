<template>
  <nav-bar></nav-bar>
  <h3 style="margin-top: 16px; margin-left: 16px">Создать заявку</h3>
  <div class="form-items" style="margin-left: 16px">
    <div class="col-lg-3">
      <label>Заявка на</label>
      <select v-model="type" class="form-control form-control-select">
        <option value="WISH">Заселение</option>
        <option value="EVICTION">Выселение</option>
        <option value="RELOCATION">Переселение</option>
        <option value="PROBLEM">Бытовая заявка</option>
      </select>
    </div>

    <form class="col-lg-3" ref="anyName" v-if="type === 'WISH'" @submit.prevent="submitWODoc">
      <label for="hostel-select">Выберите общежитие</label>
      <select id="hostel-select" class="form-select" v-model="selectedHostelName" @change="getAllFloors">
        <option v-for="hostel in hostels" v-bind:key="hostel.id">
          {{ hostel.name }}
        </option>
      </select>
      <label for="floor-select">Выберите этаж</label>
      <select id="floor-select" class="form-select" v-model="selectedFloor" @change="getAllRooms">
        <option v-for="floor in floors" v-bind:key="floor">
          {{ floor }}
        </option>
      </select>
      <label for="room-select">Выберите комнату</label>
      <select id="room-select" class="form-select" v-model="selectedRoomName" @change="getAllPlaces">
        <option v-for="room in rooms" v-bind:key="room.id">
          {{ room.name }}
        </option>
      </select>
      <label for="place-select">Выберите место</label>
      <select id="place-select" class="form-select" v-model="selectedPlaceName">
        <option v-for="place in places" v-bind:key="place.id">
          {{ place.number }}
        </option>
      </select>
      <button type="submit">Отправить заявку</button>
    </form>

    <form ref="anyName" v-if="type === 'EVICTION'" @submit.prevent="submitWODoc">
      <button type="submit">Отправить заявку</button>

    </form>

    <form ref="anyName" v-if="type === 'RELOCATION'" @submit.prevent="submitWODoc">
      <label for="hostel-select">Выберите общежитие</label>
      <select id="hostel-select" class="form-select" v-model="selectedHostelName" @change="getAllFloors">
        <option v-for="hostel in hostels" v-bind:key="hostel.id">
          {{ hostel.name }}
        </option>
      </select>
      <label for="floor-select">Выберите этаж</label>
      <select id="floor-select" class="form-select" v-model="selectedFloor" @change="getAllRooms">
        <option v-for="floor in floors" v-bind:key="floor">
          {{ floor }}
        </option>
      </select>
      <label for="room-select">Выберите комнату</label>
      <select id="room-select" class="form-select" v-model="selectedRoomName" @change="getAllPlaces">
        <option v-for="room in rooms" v-bind:key="room.id">
          {{ room.name }}
        </option>
      </select>
      <label for="place-select">Выберите место</label>
      <select id="place-select" class="form-select" v-model="selectedPlaceName">
        <option v-for="place in places" v-bind:key="place.id">
          {{ place.number }}
        </option>
      </select>
      <button type="submit">Отправить заявку</button>

    </form>

    <form ref="anyName" v-if="type === 'PROBLEM' && studentPlace > 0" @submit.prevent="submitWODoc">
      <div class="form-group form-outline mb-4 ">
        <textarea v-model="message" label="Сообщение"></textarea>
      </div>
      <button type="submit">Отправить заявку</button>
    </form>
  </div>
  <div>
    <h3 style="margin-top: 16px; text-align: center">История заявок</h3>
    <table class="table table-striped">
      <thead>
      <tr>
        <th scope="col">Общежитие</th>
        <th scope="col">Комната</th>
        <th scope="col">Место</th>
        <th scope="col">Тип</th>
        <th scope="col">Статус</th>
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
      </tr>
      </tbody>
    </table>
  </div>
</template>
<style>
.form-items {
  font-family: 'Manrope' !important;
  font-size: 16px !important;
}

.form-select {
  font-family: 'Manrope' !important;
  font-size: 16px !important;
}

.form-items label {
  font-family: 'Manrope' !important;
}
</style>
<script>
import Header from "@/components/components/Header";
import axios from "axios";

export default {
  name: 'my-claims',
  components: {
    'nav-bar': Header
  },
  data() {
    return {
      selectedPlaceName: '',
      selectedPlaceId: -1,
      selectedFloor: 0,
      selectedHostelName: '',
      selectedHostelId: -1,
      selectedRoomName: '',
      selectedRoomId: -1,
      type: 'WISH',
      message: '',
      claims: [],
      hostels: [],
      rooms: [],
      floors: [],
      places: [],
      studentPlace: 0
    }
  },
  mounted() {
    this.getAllHostels();
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/claims/student', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      console.log(response.data)
      this.claims = response.data;
    })
        .catch(error => {
          // handle error response
          console.error(error);
        });
    axios.get('http://localhost:8081/api/v1/student/place', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      console.log(response.data)
      this.selectedPlaceId = response.data.id;
      this.studentPlace = response.data.id
    })
        .catch(error => {
          // handle error response
          console.error(error);
        });
  },
  watch: {
    selectedHostelName(newVal) {
      // Find the hoster with the selected name
      const selectedHostel = this.hostels.find(h => h.name === newVal)
      // Get the id of the selected hostel, or -1 if not found
      this.selectedHostelId = selectedHostel ? selectedHostel.id : -1
      this.countFloors = selectedHostel ? selectedHostel.countFloors : -1;
    },
    selectedRoomName(newVal) {
      // Find the hoster with the selected name
      const selectedRoom = this.rooms.find(r => r.name === newVal)
      console.log(selectedRoom)
      // Get the id of the selected hostel, or -1 if not found
      this.selectedRoomId = selectedRoom ? selectedRoom.id : -1
    },
    selectedPlaceName(newVal) {
      // Find the hoster with the selected name
      const selectedPlace = this.places.find(p => p.number === newVal)
      console.log(selectedPlace)
      // Get the id of the selected hostel, or -1 if not found
      this.selectedPlaceId = selectedPlace ? selectedPlace.id : -1
    },
  },
  methods: {
    submitWODoc() {
      const claim = {
        type: this.type,
        message: this.message
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/claims?placeId=' + this.selectedPlaceId,
        method: 'POST',
        data: claim,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'application/json'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.resetForm();
        window.location.reload();
      })
    },
    // onFileChange(event) {
    //   this.file = event.target.files[0];
    // },
    // submitWithDoc() {
    //   const formData = new FormData();
    //   formData.append('message', this.message);
    //   formData.append('type', this.type);
    //   formData.append('file', this.file);
    //   let user = JSON.parse(localStorage.getItem('user'))
    //   axios({
    //     url: 'http://localhost:8081/api/v1/claims/uploadFile',
    //     method: 'POST',
    //     data: formData,
    //     headers: {
    //       Accept: '*/*',
    //       Authorization: `Bearer ${user.token}`,
    //       'Content-Type': 'multipart/form-data'
    //     },
    //   }).then(response => {
    //     console.log(JSON.stringify(response.data))
    //     this.resetForm();
    //     window.location.reload();
    //   })
    // },
    resetForm() {
      this.message = '';
      this.file = null;
      this.$refs.anyName.reset(); // reset the form element
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
    },
    getAllRooms() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get(`http://localhost:8081/api/v1/hostel/${this.selectedHostelId}/floor?floor=` + this.selectedFloor, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        }
      }).then(response => {
        // handle success response
        this.rooms = response.data;
      })
          .catch(error => {
            // handle error response
            console.error(error);
          });
    },
    getAllFloors() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get(`http://localhost:8081/api/v1/hostel/${this.selectedHostelId}/countFloor`, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        }
      }).then(response => {
        // handle success response
        this.floors = response.data;
      })
          .catch(error => {
            // handle error response
            console.error(error);
          });
    },
    getAllPlaces() {
      let user = JSON.parse(localStorage.getItem('user'));
      axios.get(`http://localhost:8081/api/v1/hostel/${this.selectedRoomId}/place`, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        }
      }).then(response => {
        // handle success response
        this.places = response.data;
        console.log(this.places)
      })
          .catch(error => {
            // handle error response
            console.error(error);
          });
    }
  }
}
</script>