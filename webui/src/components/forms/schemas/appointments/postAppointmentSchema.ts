import * as yup from 'yup'

export const postAppointmentsSchema = yup.object().shape({
  date: yup
    .date()
    .typeError('La fecha es requerida'),
  hour: yup
    .string()
    .required('La hora es requerida')
    .test('hour', 'La hora debe estar en el formato de 30 minutos', value => {
      if (value) {
        const minutes = parseInt(value.split(':')[1])
        return minutes === 0 || minutes === 30
      }
      return false
    }
  ),
})