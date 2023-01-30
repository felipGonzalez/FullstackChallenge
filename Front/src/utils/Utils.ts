export default {

    calculateAge(dateBirth: string){
        if (!dateBirth) return "";
        let date = new Date(dateBirth);
        let diff_ms = Date.now() - date.getTime();
        let age_dt = new Date(diff_ms);
        return Math.abs(age_dt.getUTCFullYear() - 1970) + " años";
      },
      getGender(gender: string) {
        if (!gender) {
          return "";
        }
        if (gender === "MALE") {
          return "Masculino";
        }
        return "Femenino";
      }

}