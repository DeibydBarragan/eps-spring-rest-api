import * as yup from 'yup'

export const postPatientSchema = yup.object().shape({
  name: yup
    .string()
    .required('El nombre es requerido')
    .min(3, 'El nombre tiene que tener al menos 3 carácteres'),
  lastname: yup
    .string()
    .required('El apellido es requerido')
    .min(3, 'El apellido tiene que tener al menos 3 carácteres'),
  cedula: yup
    .number()
    .typeError('La cedula es requerida')
    .min(1, 'La cedula tiene que tener al menos 1 carácterer')
    .max(99999999999, 'La cedula tiene que tener máximo 11 carácteres'),
  age: yup
    .number()
    .typeError('La edad es requerida')
    .min(1, 'La edad tiene que ser mínimo 1')
    .max(135, 'La edad tiene que tener máximo 135'),
  email: yup
    .string()
    .required('El email es requerido')
    .email('El email tiene que ser un email válido'),
  phone: yup
    .number()
    .typeError('El teléfono es requerido')
    .min(1000000000, 'El teléfono tiene que tener 10 carácteres')
    .max(9999999999, 'El teléfono tiene que tener 10 carácteres'),
  
})