import { useUserDetailsStore } from "../../stores/useUserDetailsStore.js";
import Myprofile from './Myprofile.vue';
import PofoReg from './PortfolioRegister.vue'
import Register from './community/Register.vue'


const member = {
  path: "/member", 
  children :[
    { path : "profile", children : [
        {path : ":id", component: Myprofile}
    ]},
    { path : "reg", component : PofoReg },
    
    { path: 'community', children: [
      { path: 'register', component: Register }
    ]}
  ],
  beforeEnter(to,from,next) {
    let userDetails = useUserDetailsStore();
    let url = `/login?returnURL=${to.path}`;
    if (!userDetails.isAuthenticated)
        next(url);
    else if (!userDetails.hasRole("ADMIN")) 
        next("/error/403");
    else 
        next();
  }
};

export default member;
