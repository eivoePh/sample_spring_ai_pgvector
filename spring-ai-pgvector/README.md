

# RAG Example

### 准备 ollama 本地模型

```sh
brew install ollama
export OLLAMA_HOST="0.0.0.0" && ollama serve
```

```sh
ollama run gemma2:2b
ollama run viosay/conan-embedding-v1:latest
```

## 准备 vector db

```sh
cd dockerfiles
docker compose up -d
```

## 将文本数据存入Vector库

[将data.txt文件中的数据存入Vector库](http://localhost:8080/load_data)

## 进行对话

[进行对话](http://localhost:8080/chat?msg=我可以用于商业用途吗)

ref: (https://github.com/benayat/rag-with-spring-ai/tree/master)
