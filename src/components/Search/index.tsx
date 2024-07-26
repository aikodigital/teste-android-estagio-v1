import { Container, ContentIcon, Icon, Input } from "./styles";

export function Search(){
    return(
        <Container>
            <Input placeholder="Para onde você quer ir?"/>
            <ContentIcon>
                <Icon name="search"/> 
            </ContentIcon>
        </Container>
    )
}