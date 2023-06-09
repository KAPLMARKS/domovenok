<template>
  <div class="ban">
    <nav-bar></nav-bar>
    <div class="row">
      <h1 style="text-align:center">Университет</h1>
      <div class="col-xl-9 col-lg-12">
        <p>API ключ</p>
        <div class="key-wrapper">
          <input class="form-control" id="api-key-wrapper" type="text" :value="key" disabled>
        </div>
        <button class="btn btn-primary" @click="copyKey">Скопировать ключ</button>
        <button class="btn btn-primary" @click="refreshKey">Обновить ключ</button>
      </div>
      <div class="col-xl-9 col-lg-12">
        <p>Cистема баллов</p>
        <div class="key-wrapper">
          <label for="budget-balls-wrapper">Баллы за бюджет</label>
          <input class="form-control" id="budget-balls-wrapper" type="text" v-model="budget">
          <label for="budget-balls-wrapper">Баллы за льготы</label>
          <input class="form-control" id="lgots-balls-wrapper" type="text"  v-model="lgots">
        </div>
        <button class="btn btn-primary" @click="updateBalls">Обновить баллы</button>
      </div>
    </div>
    <div class="row">
      <h3>Загрузка Администрации</h3>
      <h5>Загрузка Excel-файлом</h5>
      <load-excel></load-excel>
      <a class="toggle-h">
        <h5 class="toggle-h" v-on:click="isAdmin = !isAdmin">Добавить Администратора</h5>
      </a>
      <div v-if="isAdmin">
        <employee-component></employee-component>
      </div>
      <hr style="margin-top: 32px">
      <h3>Загрузка заведующих общежитиями</h3>
      <h5>Загрузка Excel-файлом</h5>
      <load-excel-manager></load-excel-manager>
      <a class="toggle-h">
        <h5 v-on:click="isManager = !isManager">Добавить заведующего общежитием</h5>
      </a>
      <div v-if="isManager">
        <hostel-manager-component></hostel-manager-component>
      </div>
      <hr style="margin-top: 32px">
      <h3 style="text-align: center">Cписок администраторов</h3>
      <list-employees></list-employees>

    </div>
  </div>
</template>
<style>
.ban {
  background-color: white;
}

.row {
  padding-left: 16px;
  padding-top: 16px;
}

#api-key-wrapper {
  width: 38em;
  height: 3em;
  padding: 15px;
}

button {
  width: 300px !important;
  height: 60px !important;
  padding: 15px 0;
  text-align: center;
  margin: 20px 10px 20px 0px !important;
  border-radius: 0px !important;
  font-weight: bold !important;
  border: 2px solid #009688 !important;
  background: transparent !important;
  color: #009688 !important;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  font-family: 'Manrope';
}

button:hover {
  border-radius: 0px;
  font-weight: bold;
  border: 2px solid #009688;
  background-color: #009688 !important;
  color: #fff !important;
}

.toggle-h {
  color: black;
  cursor: pointer;
}

.toggle-h:hover {
  color: black;
  cursor: pointer;
}

.form-wrapper-1 {
  display: flex;
  flex-direction: column;
  align-items: flex-start;
}
</style>
<script>

import Header from "@/components/components/Header";
import axios from "axios";
import LoadExcel from "@/components/components/LoadExcel";
import EmployeeComponent from "@/components/components/EmployeeComponent";
import ListEmployees from "@/components/components/ListEmployees";
import HostelManagerComponent from "@/components/components/HostelManagerComponent";
import LoadExcelManager from "@/components/components/LoadExcelManager";

export default {
  name: "main-page",
  components: {
    "nav-bar": Header,
    "load-excel": LoadExcel,
    "employee-component": EmployeeComponent,
    "list-employees": ListEmployees,
    "hostel-manager-component": HostelManagerComponent,
    "load-excel-manager": LoadExcelManager
  },
  data() {
    return {
      key: '',
      isAdmin: false,
      isManager: false,
      budget: 0,
      lgots: 0
    };
  },
  mounted() {
    let user = JSON.parse(localStorage.getItem('user'));
    axios.get('http://localhost:8081/api/v1/university/secret-key', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    })
        .then(response => this.key = response.data.key)
    axios.get('http://localhost:8081/api/v1/university/show-balls', {
      headers: {
        Accept: '*/*',
        Authorization: `Bearer ${user.token}`,
      }
    }).then(response => {
      this.budget = response.data.budget;
      this.lgots = response.data.lgots
    })
  },
  methods: {
    copyKey() {
      navigator.clipboard.writeText(this.key);
    },
    refreshKey() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios.post('http://localhost:8081/api/v1/university/refresh-secret-key', {}, {
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
        }
      })
          .then((res) => this.key = res.data.key)
          .catch(error => {
            // Display error message to user
            console.error(error);
          });
    },
    updateBalls() {
      const formData = {
        budget: this.budget,
        lgots: this.lgots
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/university/balls',
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
  },
};
</script>