import { Button, Input, Loading, Modal, Text, useModal, Popover, Row } from '@nextui-org/react'
import { useEffect, useState } from 'react'
import { useForm } from 'react-hook-form'
import { yupResolver } from '@hookform/resolvers/yup'
import InputPopover from '@/components/common/inputPopover/inputPopover'
import { toast } from 'react-toastify'
import { Pencil, ChevronDown } from 'lucide-react'
import { Doctor, Specialty } from '@/interfaces/interfaces'
import { specialties } from '@/constants/constants'
import {putItem} from "@/api/putItem";
import {postDoctorSchema} from "@/components/forms/schemas/doctors/postDoctorSchema";

type Props = {
  doctor: Doctor
}

export default function PutDoctor({ doctor }: Props) {
  // Modal state
  const { visible, setVisible } = useModal()

  // Loading fetch state
  const [isLoading, setIsLoading] = useState(false)
  
  // Modal handlers
  const handler = () => setVisible(true)
  const closeHandler = () => setVisible(false)
  
  // Specialty form state
  const [specialty, setSpecialty] = useState<Specialty>(doctor.specialty)

  // Form validation
  const { register, formState: { errors }, handleSubmit, reset, setValue } = useForm({
    resolver: yupResolver(postDoctorSchema),
  })
  
  // Reset the form when the modal is closed
  useEffect(() => {
    reset()
    setSpecialty(doctor?.specialty)
  }, [reset, setSpecialty, doctor?.specialty])

  useEffect(() => {
    setValue('specialty', specialty)
  }, [setValue, specialty])

  // Submit the form
  const onSubmit = async (formData: any) => {
    try {
      setIsLoading(true)
      await putItem('doctors', formData, doctor?.id)
      toast.success('El doctor fue actualizado correctamente')
      closeHandler()
    } catch(err) {
      let msg = 'Hubo un error al actualizar el doctor'
      if (err instanceof Error) {
        if(err.message === 'Cedula already in use') msg = 'La cédula ya está en uso por otro doctor'
        if(err.message === 'Email already in use') msg = 'El email ya está en uso por otro doctor'
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
        Editar doctor
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
            Editar doctor
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
              initialValue={doctor?.name}
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
              initialValue={doctor?.lastname}
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
              initialValue={doctor?.cedula.toString()}
              type='number'
              {...register('cedula')}
            />
          </InputPopover>
          
          {/**Specialties */}
          <Popover shouldFlip={false} placement='bottom-right'>
              <Popover.Trigger>
                <Button css={{ width: "100%" }} flat color="secondary" iconRight={<ChevronDown size={25}/>}>
                    {specialty ? specialty : 'Seleccionar especialidad'}
                </Button>
              </Popover.Trigger>
              <Popover.Content css={{p: '$4'}}>
                <Row css={{gap: '$4', flexDirection: 'column'}}>
                  {specialties.map((specialty) => (
                    <Button
                      key={specialty}
                      flat
                      size='md'
                      onClick={() => setSpecialty(specialty)}
                    >
                      {specialty}
                    </Button>
                  ))}
                </Row>
              </Popover.Content>
            </Popover>

          {/**Office */}
          <InputPopover error={errors.office}>
            <Input
              bordered
              fullWidth
              color="secondary"
              labelLeft="Consultorio"
              aria-label='Consultorio'
              initialValue={doctor?.office}
              type='number'
              {...register('office')}
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
              initialValue={doctor?.phone.toString()}
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
              initialValue={doctor?.email}
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
            Editar doctor
          </Button>
        </Modal.Footer>
      </Modal>
      
      {/**Toastify container */}
    </>
  )
}