import useGetItems from '@/components/hooks/useGetItems'
import { Container, Loading, Row, Table, Text, Popover, Pagination, Button } from '@nextui-org/react'
import React from 'react'
import { MoreHorizontal, RotateCw } from 'lucide-react'
import PostDoctor from '@/components/forms/doctors/PostDoctor'
import DeleteItem from '@/components/common/deleteItem/DeleteItem'
import PutDoctor from '@/components/forms/doctors/PutDoctor'

type Props = {}

export default function Doctors({}: Props) {
  // Pagination
  const [ doctors, loadingDoctors, pagination, actualPage, setActualPage, getDoctors, error ] = useGetItems('doctors?')

  return (
    <Container
      display='flex'
      gap={10}
      direction='column'
      justify='space-between'
      css={{
        border: "1px solid $secondary",
        borderRadius: "10px",
        padding: "20px",
        margin: "20px 0",
        minHeight: "80vh",
        position: "relative",
        '@xl': {
          width: "80%",
        },
      }}
    >
      <Row css={{ width: '100%', gap: '$5'}}>
        <PostDoctor />
        {doctors && (
          <Button onClick={() => getDoctors()} auto icon={<RotateCw size={20}/>}/>
        )}
      </Row>
      {loadingDoctors ? (
        <Loading size="xl"
          css={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
          }}
        />
      ) : error ? (
        <Text color="error" h3
          css={{
            position: "absolute",
            top: "50%",
            left: "50%",
            transform: "translate(-50%, -50%)",
          }}
        >
          Error al cargar los pacientes...
        </Text>
      ) : doctors &&
      (<>
          <Table
            color="secondary"
            aria-label="Tabla de pacientes"
            css={{
              height: "auto",
              width: "100%",
            }}
          >
            
            <Table.Header>
              <Table.Column>Nombre</Table.Column>
              <Table.Column>Apellido</Table.Column>
              <Table.Column>Cédula</Table.Column>
              <Table.Column>Especialidad</Table.Column>
              <Table.Column>Consultorio</Table.Column>
              <Table.Column>Correo electrónico</Table.Column>
              <Table.Column>Télefono</Table.Column>
              <Table.Column>Acciones</Table.Column>
            </Table.Header>
            <Table.Body>
              {doctors.map((doctor, index) => (
                <Table.Row key={`patient${index}`}>
                  <Table.Cell>{doctor?.name}</Table.Cell>
                  <Table.Cell>{doctor?.lastname}</Table.Cell>
                  <Table.Cell>{doctor?.cedula}</Table.Cell>
                  <Table.Cell>{doctor?.specialty}</Table.Cell>
                  <Table.Cell>{doctor?.office}</Table.Cell>
                  <Table.Cell>{doctor?.email}</Table.Cell>
                  <Table.Cell>{doctor?.phone}</Table.Cell>
                  <Table.Cell 
                    css={{
                      display: "flex",
                      justifyContent: "space-evenly",
                      gap: "10px",
                    }}
                  >
                    <Popover isDismissable={false} placement='bottom-right'>
                      <Popover.Trigger>
                        <Button auto flat icon={<MoreHorizontal />}/>
                      </Popover.Trigger>
                      <Popover.Content css={{ zIndex: "200 !important", p: '$4' }}>
                        <Row css={{gap: '$4', flexDirection: 'column'}}>
                          <PutDoctor doctor={doctor} />
                          <DeleteItem
                            endpoint={`doctors/${doctor?.id}`}
                            name='doctor'
                          />
                        </Row>
                      </Popover.Content>
                    </Popover>
                  </Table.Cell>
                </Table.Row>
              ))}
            </Table.Body>
          </Table>
          <Row justify='center' css={{width: "100%", mt: '$8'}}>
            <Pagination
              shadow
              noMargin
              total={pagination?.totalPages}
              page={actualPage}
              onChange={(page) => setActualPage(page)}
            />
          </Row>
        </>
        )}
      </Container>
  )
}