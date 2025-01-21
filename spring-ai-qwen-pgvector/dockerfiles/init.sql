CREATE EXTENSION IF NOT EXISTS vector;
CREATE EXTENSION IF NOT EXISTS hstore;
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

CREATE TABLE IF NOT EXISTS vector_store (
    id UUID DEFAULT uuid_generate_v4() PRIMARY KEY,
    content TEXT,
    metadata JSONB,
    embedding vector(1024) -- 1536 is the default embedding dimension
);

CREATE INDEX ON vector_store USING HNSW (embedding vector_cosine_ops);