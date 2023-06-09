<template>
  <header class="bg-success">
    <nav class="navbar navbar-expand-lg navbar-dark d-none d-lg-block" style="z-index: 2000;">
      <div class="container-fluid">
        <router-link class="nav-link" aria-current="page" to="/"><img alt="Label" src="./assets/label.png">
        </router-link>
        <ul class="navbar-nav ms-auto mb-2 mb-lg-0">
          <li class="nav-item" v-if="user && user.role === 'UNIVERSITY_ADMIN'">
            <router-link class="nav-link" to="/university" rel="nofollow">Университет</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/fond">Жилой фонд</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/list-students">Список студентов</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/list-students-with-places">Список заселенных студентов</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/claims-students-all">Заявки студентов</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/load-students">Загрузка студентов</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/load-balls">Загрузка баллов студентов</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUD_GORODOK'">
            <router-link class="nav-link" to="/statistics">Статистика</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUDENT'">
            <router-link class="nav-link" to="/my-claims">Мои заявки</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUDENT'">
            <router-link class="nav-link" to="/my-documents">Мои документы</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'STUDENT'">
            <router-link class="nav-link" to="/receipt">Квитанция</router-link>
          </li>
          <li class="nav-item" v-if="user && user.role === 'HOSTEL_MANAGER'">
            <router-link class="nav-link" to="/hostel-claims">Заявки общежития</router-link>
          </li>
          <li v-if="user" class="nav-item">
            <router-link class="nav-link" to="/"><img alt="Label" src="./assets/avatar.png" @click="logout"
                                                      style="height: 32px; filter: invert(.9)">
            </router-link>
          </li>
          <li v-else class="nav-item">
            <router-link class="nav-link btn btn-success rounded-pill" id="login-btn" to="/login">Войти</router-link>
          </li>
        </ul>
      </div>
    </nav>
  </header>
</template>
<script>
export default {
  name: 'HeaderMenu',
  computed: {
    user() {
      return JSON.parse(localStorage.getItem('user'))
    }
  },
  methods: {
    logout() {
      localStorage.removeItem('user');
      this.$router.push('/');
    }
  }
};
</script>
<style>
.bg-success {
  height: 60px;
  padding-left: 16px;
  padding-right: 16px;
  background-color: #4CAF50 !important;
  -webkit-box-shadow: 0px 4px 20px 0px rgba(34, 60, 80, 0.2);
  -moz-box-shadow: 0px 4px 20px 0px rgba(34, 60, 80, 0.2);
  box-shadow: 0px 4px 20px 0px rgba(34, 60, 80, 0.2);
}

.nav-item {
  font-size: 18px;
  font-family: 'Manrope';
  font-weight: 500 !important;
}

#login-btn {
  background-color: transparent;
  border-radius: 0px !important;
  border: none !important;
}

#login-btn:hover {
  color: #fff;
}

</style>