import { Container, ContentIcon, ContentItems, Icon, SubTitle, TextIcon } from "./styles";

export function Items(){
    return(
        <Container>
        <ContentIcon>
            <Icon name="bus" />
            <TextIcon>
                0.823
            </TextIcon>
        </ContentIcon>
        <ContentItems>
            <SubTitle>
                Recanto das emas(Zoológico)
            </SubTitle>
            <SubTitle type="PRIMARY">
                13 á 20 min
            </SubTitle>
        </ContentItems>
        <Icon type="PRIMARY" name="wifi" />
    </Container>
    )
}