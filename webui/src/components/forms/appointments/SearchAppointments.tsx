import { Row, Input, Popover, Button, Loading } from '@nextui-org/react'
import { Dispatch, SetStateAction, useState } from 'react'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import { Search, ChevronDown } from 'lucide-react'
import { searchAppointmentsSchema } from '../schemas/appointments/searchAppointmentSchema'
import InputPopover from '@/components/common/inputPopover/inputPopover'

type Props = {
  setEndpoint: Dispatch<SetStateAction<string>>
  loading: boolean
}

export default function SearchAppointments({setEndpoint, loading}: Props) {
  const [option, setOption] = useState<'patient' | 'doctor'>('patient')

  const handleOption = (option: 'patient' | 'doctor') => {
    setOption(option)
  }

  // Form validation
  const { register, formState: { errors }, handleSubmit } = useForm({
    resolver: yupResolver(searchAppointmentsSchema),
  })

  const onSubmit = async (formData: any) => {
    setEndpoint(`appointments/${option}/${formData.cedula}?`)
  }

  return (
    <Row
      css={{gap: "$4"}}
      as='form'
      aria-label='Buscar citas'
      onSubmit={handleSubmit(onSubmit)}
    >
      <InputPopover error={errors.cedula}>
        <Input
          type='text'
          labelLeft='Cédula'
          aria-label='Nombre'
          fullWidth
          clearable
          {...register('cedula')}
        />
      </InputPopover>
      <Popover placement='bottom-left'>
        <Popover.Trigger>
          <Button 
            type='button' 
            disabled={loading}
          >
            {/**
             * TO DO: LIST ICON
             */}
            Buscar por: {option === 'patient' ? 'Paciente' : 'Doctor'}
            <ChevronDown size={25}/>
          </Button>
        </Popover.Trigger>
        <Popover.Content css={{ zIndex: "200 !important", p: "$4" }}>
          <Row css={{gap: '$4', flexDirection: 'column'}}>
            <Button
              type='button'
              flat
              onClick={() => handleOption('patient')}
            >
              Paciente
            </Button>
            <Button
              type='button'
              flat
              onClick={() => handleOption('doctor')}
            >
              Doctor
            </Button>
          </Row>
        </Popover.Content>
      </Popover>
      <Button 
        auto
        type='submit'
        disabled={loading}
      >
        {loading ? <Loading color='white' type='points' size='sm'/> : <Search /> }
      </Button>
    </Row>
  )
}