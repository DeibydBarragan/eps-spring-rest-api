import { Tooltip } from '@nextui-org/react'
import React from 'react'
import { FieldError } from 'react-hook-form'

type Props = {
  error: FieldError | undefined
  children: React.ReactNode
}

const InputPopover = ({ error, children }: Props) => {
  return (
    <Tooltip
      initialVisible={true}
      visible={error?.message ? true : false}
      content={error?.message}
      color='error'
      placement='bottomStart'
      trigger='click'
      css={{
        zIndex: '10000 !important',
      }}
      style={{ width: '100%', height: '80%' }}
      as='div'
    >
      {children}
    </Tooltip>
  )
}

export default InputPopover