import { Button, Modal, Text, Loading } from '@nextui-org/react'
import { useState } from 'react'
import { Trash2 } from 'lucide-react'
import { toast } from 'react-toastify'
import { deleteItem } from '@/api/deleteItem'

type Props = {
  endpoint: string
  name: string
}

export default function DeleteItem({ endpoint, name }: Props) {
  const [visible, setVisible] = useState(false)

  // Loading fetch state
  const [isLoading, setIsLoading] = useState(false)

  // Modal handlers
  const handler = () => setVisible(true)
  const closeHandler = () => setVisible(false)

  //Fetch handler
  const handleOnConfirm = async () => {
    try {
      setIsLoading(true)
      await deleteItem(endpoint)
      closeHandler()
      toast.success(`${name} eliminado correctamente`)
    } catch(err) {
      toast.error(`Hubo un error al eliminar el/la ${name}`)
    } finally {
      setIsLoading(false)
    }
  }

  return (
    <>
      <Button
        flat
        onClick={handler}
        color='error'
        iconRight={<Trash2 size={20}/>}
      >
        Eliminar
      </Button>
      <Modal
        closeButton
        aria-labelledby="modal-title"
        open={visible}
        onClose={closeHandler}
        blur
      >
        <Modal.Header>
          <Text id="modal-title" color="error" h4>
            ¿Está seguro de eliminar el {name}?
          </Text>
        </Modal.Header>
        <Modal.Footer>
          {/**Cancel and confirm buttons */}
          <Button auto flat onClick={closeHandler}>
            Cancelar
          </Button>
          <Button 
            auto
            color='error'
            iconRight={
              isLoading ? <Loading color='secondary' type='points' size='sm'/>
              : <Trash2 size={20}/>
            }
            disabled={isLoading}
            onClick={handleOnConfirm}
          >
            Eliminar
          </Button>
        </Modal.Footer>
      </Modal>
    </>
  )
}