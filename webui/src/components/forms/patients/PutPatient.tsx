import { Button, Input, Loading, Modal, Text, useModal } from '@nextui-org/react'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import InputPopover from '@/components/common/inputPopover/inputPopover'
import { toast } from 'react-toastify'
import { Pencil } from 'lucide-react';
import { Patient } from '@/interfaces/interfaces'
import {postPatientSchema} from "@/components/forms/schemas/patients/postPatientSchema";
import {putItem} from "@/api/putItem";

type Props = {
  patient: Patient
}

export default function PutPatient({ patient }: Props) {
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
      await putItem('patients', formData, patient?.id)
      toast.success('El paciente fue actualizado correctamente')
      closeHandler()
    } catch(err) {
      let msg = 'Hubo un error al actualizar el paciente'
      if (err instanceof Error) {
        if(err.message === 'Cedula already in use') msg = 'La cédula ya está en uso por otro paciente'
        if(err.message === 'Email already in use') msg = 'El email ya está en uso por otro paciente'
      }
      toast.error(msg)
      console.log(err)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <>
      <Button flat onPress={handler} iconRight={<Pencil size={20} />}>
        Editar paciente
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
            Editar paciente
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
              initialValue={patient?.name}
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
                initialValue={patient?.lastname}
                type='text'
                {...register('lastname')}
              />
          </InputPopover>

          {/**Cedula */}
          <InputPopover error={errors.cedula}>
            <Input
              bordered
              fullWidth
              type='number'
              color="secondary"
              labelLeft="Cédula"
              aria-label='Cédula'
              initialValue={patient?.cedula.toString()}
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
              initialValue={patient?.age.toString()}
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
              initialValue={patient?.phone.toString()}
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
              initialValue={patient?.email}
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
              : <Pencil size={20}/>
            }
            disabled={isLoading}
          >
            Actualizar paciente
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}