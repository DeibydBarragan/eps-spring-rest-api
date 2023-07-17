import * as yup from 'yup'

export const searchAppointmentsSchema = yup.object().shape({
  cedula: yup
    .number()
    .typeError('La cedula es requerida')
    .min(1, 'La cedula tiene que tener mímimo 1 caracteres')
    .max(99999999999, 'La cedula tiene que tener máximo 11 caracteres'),
})