<template>
  <nav-bar></nav-bar>
  <h1 style="text-align:center; margin-top: 16px;">Загрузка студентов</h1>
  <h5 style="margin-top: 16px; margin-left: 16px">Добавить Студента</h5>
  <div class="form-wrapper-1">
    <form style="margin-left: 16px" ref="anyName" @submit.prevent="submitForm">
      <div class="form-row">
        <label>Фамилия</label>
        <input type="text" class="form-control" v-model="lastName" required>
        <label>Имя</label>
        <input type="text" class="form-control" v-model="firstName" required>
        <label>Отчество</label>
        <input type="text" class="form-control" v-model="middleName">
        <label>Почта</label>
        <input type="email" class="form-control" v-model="email" required>
        <label>Пароль</label>
        <input type="password" class="form-control" v-model="password" required>
      </div>

      <div class="form-row" style="margin-top: 16px">
        <input type="checkbox" class="custom-checkbox" id="isMale" v-model="isMale">
        <label for="isMale">Мужской пол</label>
        <input type="checkbox" class="custom-checkbox" id="isNowStatement" v-model="isNowStatement">
        <label for="isNowStatement">Нуждается ли в общежитии</label>
        <br>
        <br>
        <label>Страна</label>
        <input type="text" class="form-control" v-model="citizenship" required>
        <label>Категория обучения</label>
        <select v-model="studentCategory" class="form-control form-control-select">
          <option value="BUDGET">Бюджет</option>
          <option value="PAID">Платное</option>
          <option value="CONTRACT">Контракт</option>
        </select>
        <label>Форма обучения</label>
        <select v-model="formEducation" class="form-control form-control-select">
          <option value="FULL_TIME">Очная</option>
          <option value="EXTRAMURAL">Заочная</option>
          <option value="PART_TIME">Очно-заочная</option>
        </select>
      </div>

      <div class="form-row" style="margin-top: 16px">
        <label>Уровень образования</label>
        <select v-model="qualificationType" class="form-control form-control-select">
          <option value="BAKALAVR">Бакалавр</option>
          <option value="MAGISTR">Магистр</option>
        </select>
        <label>Курс</label>
        <input type="text" class="form-control" v-model="course" required>
        <label>Факультет</label>
        <input type="text" class="form-control" v-model="faculty" required>
        <br>
        <input type="checkbox" class="custom-checkbox" id="hasPreferential" v-model="hasPreferential">
        <label for="hasPreferential">Льготы</label>
        <br>
        <br>
        <label>Возраст</label>
        <input type="int" class="form-control" v-model="age" required>
        <button class="btn btn-primary" type="submit">Добавить</button>
      </div>
    </form>
  </div>
  <h5 style="margin-top: 16px; margin-left: 16px">Загрузка Excel-файлом</h5>
  <form style="margin-left: 16px" ref="uploadForm" @submit.prevent="submit">
    <div class="form-wrapper-1">
      <div class="form-wrapper-2">
        <label for="file-upload" class="input-file">
          Выберите файл
        </label>
        <input id="file-upload" type="file" ref="uploadExcel" @change="onExcelUpload()" class="form-control"/>
      </div>
      <div class="form-wrapper-2">
        <button class="btn btn-primary" type="button" @click="startUpload()">Загрузить файл</button>
      </div>
    </div>
  </form>
</template>
<style>

.form-control-select {
  max-width: 300px !important;
}

.custom-checkbox {
  position: absolute;
  z-index: -1;
  opacity: 0;
}

.custom-checkbox+label {
  display: inline-flex;
  align-items: center;
  user-select: none;
}
.custom-checkbox+label::before {
  content: '';
  display: inline-block;
  width: 1em;
  height: 1em;
  flex-shrink: 0;
  flex-grow: 0;
  border: 1px solid #adb5bd;
  border-radius: 0.25em;
  margin-right: 0.5em;
  background-repeat: no-repeat;
  background-position: center center;
  background-size: 50% 50%;
}
.custom-checkbox:checked+label::before {
  border-color: #009688;
  background-color: #009688;
  background-image: url("data:image/svg+xml,%3csvg xmlns='http://www.w3.org/2000/svg' viewBox='0 0 8 8'%3e%3cpath fill='%23fff' d='M6.564.75l-3.59 3.612-1.538-1.55L0 4.26 2.974 7.25 8 2.193z'/%3e%3c/svg%3e");
}
/* стили при наведении курсора на checkbox */
.custom-checkbox:not(:disabled):not(:checked)+label:hover::before {
  border-color: #b3d7ff;
}
/* стили для активного состояния чекбокса (при нажатии на него) */
.custom-checkbox:not(:disabled):active+label::before {
  background-color: #b3d7ff;
  border-color: #b3d7ff;
}
/* стили для чекбокса, находящегося в фокусе */
.custom-checkbox:focus+label::before {
  box-shadow: 0 0 0 0.2rem rgba(0, 123, 255, 0.25);
}
/* стили для чекбокса, находящегося в фокусе и не находящегося в состоянии checked */
.custom-checkbox:focus:not(:checked)+label::before {
  border-color: #80bdff;
}
/* стили для чекбокса, находящегося в состоянии disabled */
.custom-checkbox:disabled+label::before {
  background-color: #e9ecef;
}
</style>
<script>
import axios from "axios";
import Header from "@/components/components/Header";

export default {
  name: 'load-student-page',
  components: {
    "nav-bar": Header,
  },

  data() {
    return {
      formData: null,
      email: '',
      firstName: '',
      middleName: '',
      lastName: '',
      password: '',
      isMale: false,
      isNowStatement: false,
      citizenship: '',
      studentCategory: '',
      formEducation: '',
      qualificationType: '',
      course: '',
      faculty: '',
      hasPreferential: false,
      age: null
    }
  },

  methods: {
    submitForm() {
      const student = {
        email: this.email,
        firstName: this.firstName,
        middleName: this.middleName,
        lastName: this.lastName,
        password: this.password,
        isMale: this.isMale,
        isNowStatement: this.isNowStatement,
        citizenship: this.citizenship,
        studentCategory: this.studentCategory,
        formEducation: this.formEducation,
        qualificationType: this.qualificationType,
        course: parseInt(this.course),
        faculty: this.faculty,
        hasPreferential: this.hasPreferential,
        age: parseInt(this.age)
      };
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/student/',
        method: 'POST',
        data: student,
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
    },
    onExcelUpload() {
      let file = this.$refs.uploadExcel.files[0];
      this.formData = new FormData();
      this.formData.append('file', file);
    },
    startUpload() {
      let user = JSON.parse(localStorage.getItem('user'))
      axios({
        url: 'http://localhost:8081/api/v1/student/import',
        method: 'POST',
        data: this.formData,
        headers: {
          Accept: '*/*',
          Authorization: `Bearer ${user.token}`,
          'Content-Type': 'multipart/form-data'
        },
      }).then(response => {
        console.log(JSON.stringify(response.data))
        this.$refs.uploadForm.reset();
        window.location.reload();
      })
    }
  }
}
</script>