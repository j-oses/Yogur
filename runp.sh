for file in ./tests/gencode/*
do
  ./Maquina\ p/maquina-P/Main "$file" >> "$file".out
done
