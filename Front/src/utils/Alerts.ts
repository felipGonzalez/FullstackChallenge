import Swal from "sweetalert2";

export default {
  showMessage(icon: string, title: string, timer: number, position: string) {
    Swal.fire({
      position: "top-center",
      icon: icon,
      title: title,
      showConfirmButton: false,
      timer: 1500,
    });
  },

  showErrorMessage(text: string){
    Swal.fire({
        icon: 'error',
        title: 'Oops...',
        text: text,
      })
  }


};
