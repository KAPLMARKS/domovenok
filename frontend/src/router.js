import {createRouter, createWebHashHistory} from "vue-router";
import MainPage from "@/components/page/MainPage.vue";
import Requisition from "@/components/page/Requisition.vue";
import SuccessPage from "@/components/page/SuccessPage.vue";
import VerifyPage from "@/components/page/VerifyPage.vue";
import LoginPage from "@/components/page/LoginPage.vue";
import UniversityPage from "@/components/page/UniversityPage";
import FondPage from "@/components/page/FondPage";
import LoadStudentsPage from "@/components/page/LoadStudentsPage";
import ListStudentsPage from "@/components/page/ListStudentsPage";
import ClaimsListEmployeePage from "@/components/page/ClaimsListEmployeePage";
import MyClaimsPage from "@/components/page/MyClaimsPage";
import LoadStudentBalls from "@/components/page/LoadStudentBalls";
import HostelClaims from "@/components/page/HostelClaims";
import MyDocuments from "@/components/page/MyDocuments";
import ReceiptPage from "@/components/page/ReceiptPage";
import StatisticsPage from "@/components/page/StatisticsPage";
import ListStudentsPageWithPlace from "@/components/page/ListStudentsPageWithPlace";

export default createRouter({
    history: createWebHashHistory(),
    routes: [
        {path: "/", component: MainPage},
        {path: "/requisition", component: Requisition},
        {path: "/success-page", component: SuccessPage},
        {path: "/requisition/verify/:token", component: VerifyPage},
        {path: "/login", component: LoginPage},
        {path: "/university", component: UniversityPage},
        {path: "/fond", component: FondPage},
        {path: "/load-students", component: LoadStudentsPage},
        {path: "/list-students", component: ListStudentsPage},
        {path: "/claims-students-all", component: ClaimsListEmployeePage},
        {path: "/my-claims", component: MyClaimsPage},
        {path: "/load-balls", component: LoadStudentBalls},
        {path: "/hostel-claims", component: HostelClaims},
        {path: "/my-documents", component: MyDocuments},
        {path: "/receipt", component: ReceiptPage},
        {path: "/statistics", component: StatisticsPage},
        {path: "/list-students-with-places", component: ListStudentsPageWithPlace}
    ],
})