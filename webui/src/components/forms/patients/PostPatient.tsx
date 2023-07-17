import { Button, Input, Loading, Modal, Text, useModal } from '@nextui-org/react'
import { useEffect, useState } from 'react'
import { postPatientSchema } from '../schemas/patients/postPatientSchema'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import InputPopover from '@/components/common/inputPopover/inputPopover'
import { insertItem } from '@/api/InsertItem'
import { toast } from 'react-toastify'
import { UserPlus, UserPlus2 } from 'lucide-react';

type Props = {}

export default function PostPatient({}: Props) {
  // Modal state
  const { visible, setVisible } = useModal()

  // Loading fetch state
  const [isLoading, setIsLoading] = useState(false)
  
  // Modal handlers
  const handler = () => setVisible(true)
  const closeHandler = () => setVisible(false)
  
  // Form validation
  const { register, formState: { errors }, handleSubmit, reset } = useForm({
    resolver: yupResolver(postPatientSchema),
  })
  
  // Reset the form when the modal is closed
  useEffect(() => {
    reset()
  }, [visible, reset])
  
  // Submit the form
  const onSubmit = async (formData: any) => { 
    try {
      setIsLoading(true)
      await insertItem('patients', formData)
      toast.success('Paciente añadido correctamente')
      reset()
    } catch (err) {
      let msg = 'Hubo un error al guardar el paciente'
      if (err instanceof Error) {
        if(err.message === 'Cedula already in use') msg = 'La cédula ya está en uso'
        if(err.message === 'Email already in use') msg = 'El email ya está en uso'
      }
      toast.error(msg)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <>
      <Button onPress={handler} iconRight={<UserPlus2 size={23}/>}>
        Añadir paciente
      </Button>
      <Modal
        closeButton
        aria-labelledby="modal-title"
        open={visible}
        onClose={closeHandler}
        blur
        as='form'
        onSubmit={handleSubmit(onSubmit)}
      >
        <Modal.Header>
          <Text id="modal-title" h4>
            Añadir paciente
          </Text>
        </Modal.Header>
        <Modal.Body>
          <Text h5>
            Datos básicos
          </Text>
          {/**Nombre */}
          <InputPopover error={errors.name}>
            <Input
              clearable
              bordered
              fullWidth
              color="secondary"
              labelLeft="Nombre"
              aria-label='Nombre'
              type='text'
              {...register('name')}
            />
          </InputPopover>
          
          {/**Lastname */}
          <InputPopover error={errors.lastname}>
              <Input
                clearable
                bordered
                fullWidth
                color="secondary"
                labelLeft="Apellido"
                aria-label='Apellido'
                type='text'
                {...register('lastname')}
              />
          </InputPopover>

          {/**Cedula */}
          <InputPopover error={errors.cedula}>
            <Input
              bordered
              fullWidth
              color="secondary"
              labelLeft="Cédula"
              aria-label='Cédula'
              type='number'
              {...register('cedula')}
            />
          </InputPopover>
          
          {/**Age */}
          <InputPopover error={errors.age}>
            <Input
              bordered
              fullWidth
              color="secondary"
              labelLeft="Edad"
              aria-label='Edad'
              type='number'
              {...register('age')}
            />
          </InputPopover>

          <Text h5>
            Datos de contacto
          </Text>

          {/**Phone */}
          <InputPopover error={errors.phone}>
            <Input
              bordered
              fullWidth
              color="secondary"
              labelLeft="Teléfono"
              aria-label='Teléfono'
              type='number'
              {...register('phone')}
            />
          </InputPopover>

          {/**Email */}
          <InputPopover error={errors.email}>
            <Input
              clearable
              bordered
              fullWidth
              color="secondary"
              labelLeft="Correo electrónico"
              aria-label='Correo electrónico'
              type='text'
              {...register('email')}
            />
          </InputPopover>
          
        </Modal.Body>
        <Modal.Footer>
          {/**Cancel and submit buttons */}
          <Button auto flat color="error" onClick={closeHandler}>
            Cancelar
          </Button>
          <Button 
            auto
            type='submit' 
            color='secondary'
            iconRight={
              isLoading ? <Loading color='secondary' type='points' size='sm'/>
              : <UserPlus size={20}/>
            }
            disabled={isLoading}
          >
            Añadir paciente
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}