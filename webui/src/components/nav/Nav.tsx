import { Link, Navbar } from '@nextui-org/react'
import React from 'react'
import { links } from './nav.data'

type Props = {
  setSection: (section: string) => void
  section: string
}

export default function Nav({setSection, section}: Props) {
  return (
    <Navbar isBordered variant="sticky" css={{zIndex: "1000 !important"}}>
      <Navbar.Toggle aria-label="toggle navigation"
        css={{
          '@xs': {
            display: 'none',
          },
        }}
      />
      <Navbar.Content hideIn="xs" variant="highlight-rounded">
        {links.map((link, index) => (
          <Navbar.Link 
            key={`link${index}`}
            onClick={() => setSection(link.id)}
            isActive={section === link.id}
          >
            {link.name}
          </Navbar.Link>
        ))}
      </Navbar.Content>
      <Navbar.Collapse>
        {links.map((link, index) => (
          <Navbar.CollapseItem key={index}>
            <Link
              color='inherit'
              onClick={() => setSection(link.id)}
            >
              {link.name}
            </Link>
          </Navbar.CollapseItem>
        ))}
      </Navbar.Collapse>
    </Navbar>
  )
}