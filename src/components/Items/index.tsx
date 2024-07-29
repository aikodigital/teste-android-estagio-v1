import { Container, ContentIcon, ContentItems, Icon, SubTitle, TextIcon } from "./styles";

type ItemsProps = {
    number: string;
    destiny: string;
    time: string;
}

export function Items({ number, destiny, time }: ItemsProps) {
    return (
        <Container>
            <ContentIcon>
                <Icon name="bus" />
                <TextIcon>
                    {number}
                </TextIcon>
            </ContentIcon>
            <ContentItems>
                <SubTitle>
                    {destiny}
                </SubTitle>
                <SubTitle type="PRIMARY">
                    {time}
                </SubTitle>
            </ContentItems>
            <Icon type="PRIMARY" name="wifi" />
        </Container>
    )
}
