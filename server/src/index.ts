import express, { Request, Response } from 'express';
import dotenv from 'dotenv';
import cors from 'cors'
import { getPositionsController,
         getLines,
         getParadeByLine } from './services/transporteController';




dotenv.config();

const app = express();
const port = 3000;

app.use(cors());
app.use(express.json());

app.get('/', (req: Request, res: Response) => {
  res.send('Hello, World!');
}); 

// app.get('/autentication', autenticationController);7

app.get('/positions', getPositionsController);

app.get('/linhas', getLines); 

app.get('/paradas', getParadeByLine);

app.listen(port, () => {
  console.log(`Server is running at http://localhost:${port}`);
});
